package com.android.dd.wanandroidcompose.data

import com.android.dd.wanandroidcompose.data.entity.User
import com.android.dd.wanandroidcompose.data.serializer.user
import com.dd.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

object AccountManager {

    var isLogin = false

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    val accountState: StateFlow<AccountState> = Utils.getApp()
        .user
        .data
        .distinctUntilChanged()
        .map {
            if (it.id.isEmpty() || it.username.isEmpty()) {
                AccountState.LogOut
            } else {
                AccountState.LogIn(true, it)
            }
        }.stateIn(
            scope,
            SharingStarted.WhileSubscribed(5_000),
            AccountState.LogOut
        )


}


sealed interface AccountState {

    object LogOut : AccountState

    data class LogIn(val isFromCookie: Boolean, val user: User = User()) : AccountState
}

inline val AccountState.isLogin: Boolean
    get() {
        return this is AccountState.LogIn
    }
