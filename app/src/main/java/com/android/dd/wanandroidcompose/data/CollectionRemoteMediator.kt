package com.android.dd.wanandroidcompose.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.data.entity.CollectBean
import com.android.dd.wanandroidcompose.data.entity.CollectRemoteKey
import com.android.dd.wanandroidcompose.net.HttpService
import com.android.dd.wanandroidcompose.utils.net.NetworkUtils
import com.dd.utils.Utils
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CollectionRemoteMediator @Inject constructor(
    private val service: HttpService,
) : RemoteMediator<Int, CollectBean>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CollectBean>
    ): MediatorResult {
        return try {
            /*
          1.LoadType.REFRESH：首次访问 或者调用 PagingDataAdapter.refresh() 触发
          2.LoadType.PREPEND：在当前列表头部添加数据的时候时触发，实际在项目中基本很少会用到直接返回 MediatorResult.Success(endOfPaginationReached = true) ，参数 endOfPaginationReached 表示没有数据了不在加载
          3.LoadType.APPEND：加载更多时触发，这里获取下一页的 key, 如果 key 不存在，表示已经没有更多数据，直接返回 MediatorResult.Success(endOfPaginationReached = true) 不会在进行网络和数据库的访问
           */

            val pageKey: Int? = when (loadType) {
                //首次访问 或者调用 PagingDataAdapter.refresh()时
                LoadType.REFRESH -> null
                //在当前加载的数据集的开头加载数据时
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                //下拉加载更多时
                LoadType.APPEND -> {
                    //使用remoteKey来获取下一个或上一个页面。
                    val remoteKey = appRoom.withTransaction {
                        appRoom.collectRemoteKeyDao.remoteKeysArticleId()
                    }
                    //remoteKey' null '，这意味着在初始刷新后没有加载任何项目，也没有更多的项目要加载。
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(true)
                    }
                    remoteKey.nextKey
                }
            }
            val page = pageKey ?: 0
            if (NetworkUtils.isOnline) {
                return MediatorResult.Error(
                    Throwable(
                        Utils.getApp().getString(R.string.network_exception_please_try_again)
                    )
                )
            }
            val data = service.getCollectList(page).data?.datas ?: arrayListOf()

            val endOfPaginationReached = data.isEmpty()

            appRoom.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //如果为刷新，则清空列表
                    appRoom.collectRemoteKeyDao.clearRemoteKeys()
                    appRoom.collectionDao.delAll()
                }
                val nextKey = if (endOfPaginationReached) null else page + 1
                appRoom.collectRemoteKeyDao.add(
                    CollectRemoteKey(
                        databaseId = 1,
                        nextKey = nextKey,
                    )
                )
                appRoom.collectionDao.addAll(data)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }
}