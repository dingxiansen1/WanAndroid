package com.android.dd.wanandroidcompose.ui.author

import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.Category
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: AuthorRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    var uiState: StateFlow<List<Category>> = repository.authorTitle
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getPaging(cid: Int) =
        repository.getPager(cid)

    fun collection(article: Article) {
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }
}