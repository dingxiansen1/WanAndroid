package com.android.dd.wanandroidcompose.ui.project

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.Category
import com.android.dd.wanandroidcompose.ui.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val repository: ProjectRepository,
    private val collectionRepository: CollectionRepository,
) : BaseViewModel() {

    private val pagerMap = mutableStateMapOf<Int, Flow<PagingData<Article>>>()
    var uiState: StateFlow<List<Category>> = repository.projectTitle
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun getPaging(cid: Int) : Flow<PagingData<Article>> {
        if (!pagerMap.containsKey(cid)) {
            pagerMap[cid] = repository.getPager(cid)
        }
        return pagerMap[cid]!!
    }

    fun collection(article: Article){
        viewModelScope.launch {
            collectionRepository.collectArticle(article)
        }
    }
}