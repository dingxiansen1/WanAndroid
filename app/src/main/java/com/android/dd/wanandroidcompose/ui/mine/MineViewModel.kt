package com.android.dd.wanandroidcompose.ui.mine

import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.ui.login.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel() {

    val accountState = AccountManager.accountState

}