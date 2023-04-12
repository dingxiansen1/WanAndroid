package com.android.dd.wanandroidcompose.ui.tool

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.ToolBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ToolViewModel @Inject constructor(
    private val repository: ToolRepository,
) : BaseViewModel() {

    val uiState: StateFlow<ToolUiState> = repository.getList()
        .map {
            ToolUiState(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ToolUiState()
        )

}

data class ToolUiState(
    val list: List<ToolBean> = emptyList(),
    val lazyListState: LazyListState = LazyListState(),
)