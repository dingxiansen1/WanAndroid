package com.android.dd.wanandroidcompose.ui.search

import androidx.paging.*
import com.android.dd.wanandroidcompose.data.appRoom
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.serializer.hotKey
import com.android.dd.wanandroidcompose.net.HttpService
import com.dd.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: HttpService,
) {


    private val searchHistory = appRoom.searchHistoryDao.querySearchHistory()

    private val hotKey = Utils.getApp().hotKey
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.hotKey.ifEmpty {
                val data = service.getSearchHotKey().data ?: emptyList()
                Utils.getApp().hotKey.updateData {
                    it.copy(hotKey = data)
                }
                data
            }
        }.distinctUntilChanged()

    val uiState = hotKey.combine(searchHistory) { hotKey, searchHistory ->
        SearchUiState(hotKey, searchHistory)
    }.distinctUntilChanged()

    fun getPager(key: String): Flow<PagingData<Article>> {
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
                                service.queryBySearchKey(page, key).data?.datas ?: emptyList()
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