package com.android.dd.wanandroidcompose.ui.tool

import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ToolRepository @Inject constructor(
    private val service: HttpService,
) {

    fun getList() = flow {
        val data = service.getToolList()
        emit(data.data ?: emptyList())
    }


}