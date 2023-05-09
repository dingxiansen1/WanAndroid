package com.android.dd.wanandroidcompose.ui.navigator.navigator

import androidx.datastore.core.DataStore
import com.android.dd.wanandroidcompose.data.entity.NavigatorTab
import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NavigatorRepository @Inject constructor(
    private val service: HttpService,
    private val navigatorTabDataStore: DataStore<NavigatorTab>,
) {

    val navigatorTab = navigatorTabDataStore
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.navigatorTab.ifEmpty {
                val data = service.getNavigationList().data ?: emptyList()
                navigatorTabDataStore.updateData {
                    it.copy(navigatorTab = data)
                }
                data
            }
        }


}