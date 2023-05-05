package com.android.dd.wanandroidcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _toastMsg = Channel<String>(Channel.BUFFERED)
    val toastMsg = _toastMsg.receiveAsFlow()
    fun showToast(msg: String) {
        viewModelScope.launch {
            _toastMsg.send(msg)
        }

    }

}