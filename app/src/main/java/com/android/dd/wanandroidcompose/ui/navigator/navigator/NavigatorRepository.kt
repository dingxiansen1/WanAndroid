package com.android.dd.wanandroidcompose.ui.navigator.navigator

import com.android.dd.wanandroidcompose.data.serializer.navigatorTab
import com.android.dd.wanandroidcompose.net.HttpService
import com.dd.utils.Utils
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NavigatorRepository @Inject constructor(
    private var service: HttpService,
) {

    val navigatorTab = Utils.getApp().navigatorTab
        .data
        .map {
            //如果本地存储数据为空，就去网络获取
            it.navigatorTab.ifEmpty {
                val data = service.getNavigationList().data ?: emptyList()
                Utils.getApp().navigatorTab.updateData {
                    it.copy(navigatorTab = data)
                }
                data
            }
        }


}