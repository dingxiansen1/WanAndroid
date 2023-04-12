package com.android.dd.wanandroidcompose.ui.message

import androidx.compose.foundation.lazy.LazyListState
import androidx.paging.PagingData
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.entity.MsgBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository,
) : BaseViewModel() {

    val uiState = MutableStateFlow(
        MessageUiState(
            unReadPagingData = repository.getUnReadMsgPagingData(),
            readPagingData = repository.getReadMsgPagingData(),
        )
    )

}

data class MessageUiState(
    val unReadPagingData: Flow<PagingData<MsgBean>>,
    val unReadLazyListState: LazyListState = LazyListState(),
    val readPagingData: Flow<PagingData<MsgBean>>,
    val readLazyListState: LazyListState = LazyListState(),
)