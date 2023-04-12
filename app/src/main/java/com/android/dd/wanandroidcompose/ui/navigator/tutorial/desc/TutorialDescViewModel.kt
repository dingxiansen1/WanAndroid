package com.android.dd.wanandroidcompose.ui.navigator.tutorial.desc

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.navigator.tutorial.TutorialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TutorialDescViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: TutorialRepository,
) : BaseViewModel() {

    val cid = savedStateHandle.get<Int>(RouteName.Arguments.cid) ?: 0

    var uiState: StateFlow<List<Article>> = repository.getTutorialChapterList(cid)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val lazyListState = MutableStateFlow(LazyListState())
}