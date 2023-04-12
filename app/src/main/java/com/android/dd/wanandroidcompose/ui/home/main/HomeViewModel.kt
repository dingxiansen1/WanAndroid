package com.android.dd.wanandroidcompose.ui.home.main

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.HomeBanner
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    val banner: StateFlow<List<HomeBanner>> = repository.getBanner()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            arrayListOf()
        )

    val uiState : Flow<PagingData<Article>> = repository.getPager().cachedIn(viewModelScope)

    val lazyListState = MutableStateFlow(LazyListState())

    fun collection(article: Article){
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }

}