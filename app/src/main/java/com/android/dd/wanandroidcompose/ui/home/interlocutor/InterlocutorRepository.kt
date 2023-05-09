package com.android.dd.wanandroidcompose.ui.home.interlocutor

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.BasicRemoteMediator
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InterlocutorRepository @Inject constructor(
    private var service: HttpService,
    private val appRoom: AppDatabase,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPager(): Flow<PagingData<Article>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            remoteMediator = BasicRemoteMediator(RemoteKeyType.Interlocutor,appRoom) {
                service.getWendaData(it).data?.datas ?: arrayListOf()
            },
            pagingSourceFactory = {
                appRoom.dao.getPagingSource(RemoteKeyType.Interlocutor)
            }
        ).flow
    }

}