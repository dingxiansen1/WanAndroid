package com.android.dd.wanandroidcompose.ui.project

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.BasicRemoteMediator
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.ProjectTab
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private var service: HttpService,
    private val projectTabDataStore: DataStore<ProjectTab>,
    private val appRoom: AppDatabase,
) {

    val projectTitle = projectTabDataStore
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.projectTab.ifEmpty {
                val data = service.getProjectTitleList().data ?: emptyList()
                projectTabDataStore.updateData {
                    it.copy(projectTab = data)
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
            remoteMediator = BasicRemoteMediator(RemoteKeyType.Project, appRoom) {
                service.getProjectPageList(it, cid).data?.datas ?: arrayListOf()
            },
            pagingSourceFactory = {
                appRoom.dao.getPagingSourceByCid(RemoteKeyType.Project, cid)
            }
        ).flow
    }
}