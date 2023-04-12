package com.android.dd.wanandroidcompose.ui.message

import androidx.paging.*
import com.android.dd.wanandroidcompose.data.entity.MsgBean
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val service: HttpService
) {
    /**
     * 未读消息Flow
     */
    fun getUnReadMsgPagingData(): Flow<PagingData<MsgBean>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                object : PagingSource<Int, MsgBean>() {
                    override fun getRefreshKey(state: PagingState<Int, MsgBean>): Int {
                        return 0
                    }

                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MsgBean> {
                        return try {
                            val page = params.key ?: 0
                            val data = service.getUnReadMessageList(page).data?.datas ?: emptyList()
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

    /**
     * 已读消息Flow
     */
    fun getReadMsgPagingData(): Flow<PagingData<MsgBean>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                object : PagingSource<Int, MsgBean>() {
                    override fun getRefreshKey(state: PagingState<Int, MsgBean>): Int {
                        return 0
                    }

                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MsgBean> {
                        return try {
                            val page = params.key ?: 0
                            val data =
                                service.getReadiedMessageList(page).data?.datas ?: emptyList()
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