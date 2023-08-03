package com.android.dd.wanandroidcompose.ui.search.result

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.SearchHistory
import com.android.dd.wanandroidcompose.manager.SnackbarManager
import com.android.dd.wanandroidcompose.manager.UiText
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import com.android.dd.wanandroidcompose.ui.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: SearchRepository,
    private val collectionRepository: CollectionRepository,
    private val snackbarManager: SnackbarManager,
) : BaseViewModel() {

    val key = savedStateHandle[RouteName.Arguments.key] ?: ""

    var uiState = MutableStateFlow(SearchResultUiState(key, repository.getPager(key)))
        private set

    fun search() {
        uiState.value = uiState.value.copy(
            pagingData = repository.getPager(uiState.value.searchKey)
        )
        addSearchHistory()
    }

    fun updateSearchKey(newKey: String) {
        uiState.value = uiState.value.copy(
            searchKey = newKey
        )
    }

    private fun addSearchHistory() {
        viewModelScope.launch {
            repository.addSearchHistory(SearchHistory(uiState.value.searchKey))
        }
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

data class SearchResultUiState(
    val searchKey: String = "",
    val pagingData: Flow<PagingData<Article>>,
    val lazyListState: LazyListState = LazyListState(),
)