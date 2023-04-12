package com.android.dd.wanandroidcompose.ui.navigator.tutorial

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Classify
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val repository: TutorialRepository,
) : BaseViewModel() {

    var uiState: StateFlow<List<Classify>> = repository.tutorialList
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val lazyListState = MutableStateFlow(LazyListState())
}