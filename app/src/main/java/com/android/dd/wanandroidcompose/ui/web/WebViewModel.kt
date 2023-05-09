package com.android.dd.wanandroidcompose.ui.web

import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.dao.ArticleDao
import com.android.dd.wanandroidcompose.data.entity.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val articleDao: ArticleDao
) : BaseViewModel() {

    suspend fun queryByLink(webUrl: String): Article? {
        return articleDao.queryByLink(webUrl)
    }

    suspend fun addHistory(data: Article) {
        articleDao.add(data)
    }

}