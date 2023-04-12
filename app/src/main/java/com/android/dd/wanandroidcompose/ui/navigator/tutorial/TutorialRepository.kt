package com.android.dd.wanandroidcompose.ui.navigator.tutorial

import com.android.dd.wanandroidcompose.net.HttpService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TutorialRepository @Inject constructor(
    private var service: HttpService,
) {

    val tutorialList = flow {
        val data = service.getTutorialList().data ?: emptyList()
        emit(data)
    }

    fun getTutorialChapterList(cid: Int) = flow {
        val data = service.getTutorialChapterList(cid).data?.datas ?: emptyList()
        emit(data)
    }

}