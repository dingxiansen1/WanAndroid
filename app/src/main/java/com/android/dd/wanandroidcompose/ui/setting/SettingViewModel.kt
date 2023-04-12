package com.android.dd.wanandroidcompose.ui.setting

import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.entity.User
import com.android.dd.wanandroidcompose.data.serializer.user
import com.android.dd.wanandroidcompose.ui.login.UserRepository
import com.dd.basiccompose.theme.ThemeUtils
import com.dd.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel() {

    val accountState = AccountManager.accountState

    fun logout() {
        viewModelScope.launch {
            val result = repository.logout().errorCode
            if (result == Constant.Succee) {
                Utils.getApp().user.updateData {
                    User()
                }
            }
        }
    }

}