package com.android.dd.wanandroidcompose.ui.search

import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.appRoom
import com.android.dd.wanandroidcompose.data.entity.HotKey
import com.android.dd.wanandroidcompose.data.entity.SearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : BaseViewModel() {

    var uiState: StateFlow<SearchUiState> = repository.uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState()
        )
    var searchKey = MutableStateFlow("")
        private set

    fun updateSearchKey(newKey: String) {
        searchKey.update {
            newKey
        }
    }

    fun addSearchHistory(key: String = searchKey.value) {
        viewModelScope.launch {
            appRoom.searchHistoryDao.add(SearchHistory(key))
        }
    }
}

data class SearchUiState(
    val hotKey: List<HotKey> = emptyList(),
    val searchHistory: List<SearchHistory> = emptyList(),
)