package com.android.dd.wanandroidcompose.ui.collection

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.CollectionRemoteMediator
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.CollectBean
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val service: HttpService,
    private val collectionRemoteMediator: CollectionRemoteMediator,
    private val appRoom: AppDatabase,
) {

    /**
     * 收藏或取消收藏
     **/
    suspend fun collectArticle(article: Article): Int {
        val data =
            if (article.collect) service.unCollectArticle(article.id) else service.collectArticle(
                article.id
            )
        if (data.errorCode == Constant.Succee) { //成功则修改数据库数据
            appRoom.dao.collect(article.id, if (article.collect) 0 else 1)
            return Constant.Succee
        }
        return Constant.Error
    }

    /**
     * 取消收藏并移除列表
     **/
    suspend fun unCollectArticle(article: CollectBean): Int {
        val data = service.unCollectArticle(article.id)
        if (data.errorCode == Constant.Succee) { //成功则修改数据库数据
            appRoom.collectionDao.del(article)
            appRoom.dao.collect(article.id, 0)
            return Constant.Succee
        }
        return Constant.Error
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(): Flow<PagingData<CollectBean>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            remoteMediator = collectionRemoteMediator,
            pagingSourceFactory = {
                appRoom.collectionDao.getPagingSource()
            }
        ).flow
    }
}
