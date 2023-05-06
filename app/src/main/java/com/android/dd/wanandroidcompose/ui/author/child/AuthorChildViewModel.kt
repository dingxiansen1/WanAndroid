package com.android.dd.wanandroidcompose.ui.author.child

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.author.AuthorRepository
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WechatChildViewModel @Inject constructor(
    private val repository: AuthorRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    var uiState = MutableStateFlow(WechatChildUiState())
        private set

    fun getPaging(cid: Int) {
        uiState.value = uiState.value.copy(data = repository.getPager(cid))
    }
    fun collection(article: Article){
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }
}

data class WechatChildUiState(
    val data: Flow<PagingData<Article>>? = null,
    val listState: LazyListState = LazyListState(),
)