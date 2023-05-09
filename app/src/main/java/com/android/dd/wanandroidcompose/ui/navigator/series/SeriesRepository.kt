package com.android.dd.wanandroidcompose.ui.navigator.series

import androidx.datastore.core.DataStore
import androidx.paging.*
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.SeriesTab
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val service: HttpService,
    private val seriesTabDataStore: DataStore<SeriesTab>,
) {

    val seriesTab = seriesTabDataStore
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.seriesTab.ifEmpty {
                val data = service.getTreeList().data ?: emptyList()
                seriesTabDataStore.updateData {
                    it.copy(seriesTab = data)
                }
                data
            }
        }

    fun getPager(cid: Int): Flow<PagingData<Article>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                object : PagingSource<Int, Article>() {
                    override fun getRefreshKey(state: PagingState<Int, Article>): Int {
                        return 0
                    }

                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
                        return try {
                            val page = params.key ?: 0
                            val data =
                                service.getSeriesDetailList(page, cid).data?.datas ?: emptyList()
                            return LoadResult.Page(
                                data = data,
                                prevKey = if (page == 0) null else page.minus(1),
                                nextKey = if (data.isEmpty()) null else page.plus(1)
                            )
                        } catch (e: Exception) {
                            return LoadResult.Error(e)
                        }
                    }

                }
            }
        ).flow
    }
}