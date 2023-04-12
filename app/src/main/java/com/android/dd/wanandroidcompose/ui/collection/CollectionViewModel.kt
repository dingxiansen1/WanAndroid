package com.android.dd.wanandroidcompose.ui.collection

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.CollectBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: CollectionRepository,
) : BaseViewModel() {

    var uiState = MutableStateFlow(
        CollectionUiState(
            pagingData = repository.getPager()
        )
    )

    fun collection(article: CollectBean) {
        viewModelScope.launch {
            repository.unCollectArticle(article)
        }
    }
}

data class CollectionUiState(
    val pagingData: Flow<PagingData<CollectBean>>,
    val lazyListState: LazyListState = LazyListState(),
)