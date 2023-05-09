package com.android.dd.wanandroidcompose.ui.author

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.BasicRemoteMediator
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.AuthorTab
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthorRepository @Inject constructor(
    private val service: HttpService,
    private val authorTab: DataStore<AuthorTab>,
    private val appRoom: AppDatabase,
) {

    val authorTitle = authorTab
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.authorTab.ifEmpty {
                val data = service.getAuthorTitleList().data ?: emptyList()
                authorTab.updateData {
                    it.copy(authorTab = data)
                }
                data
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(cid: Int): Flow<PagingData<Article>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            remoteMediator = BasicRemoteMediator(RemoteKeyType.Author,appRoom) {
                service.getAuthorArticles(it, cid).data?.datas ?: arrayListOf()
            },
            pagingSourceFactory = {
                appRoom.dao.getPagingSourceByCid(RemoteKeyType.Author, cid)
            }
        ).flow
    }
}