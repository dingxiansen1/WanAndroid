package com.android.dd.wanandroidcompose.ui.navigator.series

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.Series
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val repository: SeriesRepository,
) : BaseViewModel() {

    var uiState: StateFlow<List<Series>> = repository.seriesTab
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    val lazyListState = MutableStateFlow(LazyListState())
}