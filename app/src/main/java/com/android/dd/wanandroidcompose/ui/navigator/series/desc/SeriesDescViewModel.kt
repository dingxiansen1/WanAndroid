package com.android.dd.wanandroidcompose.ui.navigator.series.desc

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.ToastData
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import com.android.dd.wanandroidcompose.ui.navigator.series.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDescViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: SeriesRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    val cid = savedStateHandle.get<Int>(RouteName.Arguments.cid) ?: 0

    var uiState = MutableStateFlow(
        SeriesDescUiState(
            pagingData = repository.getPager(cid).cachedIn(viewModelScope)
        )
    )
        private set

    fun collection(article: Article) {
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }

    fun showToast(toastData: ToastData) {
        uiState.value = uiState.value.copy(toastMsg = toastData)
    }

    fun closeToast() {
        uiState.value = uiState.value.copy(toastMsg = ToastData(false, ""))
    }
}

data class SeriesDescUiState(
    val pagingData: Flow<PagingData<Article>>,
    val lazyListState: LazyListState = LazyListState(),
    val toastMsg: ToastData = ToastData(),
)
