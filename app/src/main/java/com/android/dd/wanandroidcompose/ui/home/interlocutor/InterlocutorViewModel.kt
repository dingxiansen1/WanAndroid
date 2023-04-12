package com.android.dd.wanandroidcompose.ui.home.interlocutor

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterlocutorViewModel @Inject constructor(
    private val repository: InterlocutorRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    var uiState = MutableStateFlow(
        InterlocutorUiState(
            pagingData = repository.getPager().cachedIn(viewModelScope)
        )
    )

    fun collection(article: Article) {
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }
}

data class InterlocutorUiState(
    val pagingData: Flow<PagingData<Article>>,
    val lazyListState: LazyListState = LazyListState(),
)