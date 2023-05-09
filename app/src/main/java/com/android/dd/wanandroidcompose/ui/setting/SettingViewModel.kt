package com.android.dd.wanandroidcompose.ui.setting

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.entity.User
import com.android.dd.wanandroidcompose.data.serializer.user
import com.android.dd.wanandroidcompose.ui.login.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: UserRepository,
    private val application: Application
) : BaseViewModel() {

    val accountState = AccountManager.accountState

    fun logout() {
        viewModelScope.launch {
            val result = repository.logout().errorCode
            if (result == Constant.Succee) {
                application.user.updateData {
                    User()
                }
            }
        }
    }

}