package com.android.dd.wanandroidcompose.ui.history

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.dao.ArticleDao
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.manager.SnackbarManager
import com.android.dd.wanandroidcompose.manager.UiText
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val historyDao: ArticleDao,
    private val snackbarManager: SnackbarManager,
) : BaseViewModel() {

    val uiState = MutableStateFlow(HistoryUiState(getPager()))

    private fun getPager(): Flow<PagingData<Article>> {
        val config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            initialLoadSize = 20,
            enablePlaceholders = false,
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                historyDao.getPagingSource(RemoteKeyType.History)
            }
        ).flow
    }


    fun collection(article: Article) {
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }

    fun showToast(msg: String) {
        snackbarManager.showMessage(
            uiText = UiText.DynamicString(msg)
        )
    }
}

data class HistoryUiState(
    val pagingData: Flow<PagingData<Article>>,
    val lazyListState: LazyListState = LazyListState(),
)
