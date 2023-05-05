package com.android.dd.wanandroidcompose.ui.login

import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.AppDataStore
import com.android.dd.wanandroidcompose.data.serializer.user
import com.dd.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    var uiState = MutableStateFlow(
        LoginUiState(
            AppDataStore.account.getSync() ?: "",
            AppDataStore.password.getSync() ?: "",
        )
    )

    fun updateAccount(string: String) {
        uiState.value = uiState.value.copy(account = string)
    }

    fun updatePassword(string: String) {
        uiState.value = uiState.value.copy(password = string)
    }

    fun updateLoginState() {
        uiState.value = uiState.value.copy(isRegisterState = uiState.value.isRegisterState.not())
    }

    fun login() {
        viewModelScope.launch {
            if (uiState.value.account.isEmpty() || uiState.value.password.isEmpty()) {
                showToast("账号或密码为空")
                return@launch
            }

            if (uiState.value.isRegisterState.not()) {
                val result = repository.login(uiState.value.account, uiState.value.password)
                if (result.errorCode == Constant.Succee && result.data != null) {
                    Utils.getApp().user.updateData {
                        result.data!!
                    }
                    AppDataStore.account.set(uiState.value.account)
                    AppDataStore.password.set(uiState.value.password)
                    uiState.value = uiState.value.copy(login = true)
                } else {
                    showToast(result.errorMsg)
                }
            } else {
                val result = repository.register(uiState.value.account, uiState.value.password)
                if (result.errorCode == Constant.Succee && result.data != null) {
                    Utils.getApp().user.updateData {
                        result.data!!
                    }
                    AppDataStore.account.set(uiState.value.account)
                    AppDataStore.password.set(uiState.value.password)
                    uiState.value = uiState.value.copy(login = true)
                } else {
                    showToast(result.errorMsg)
                }
            }
        }
    }
}

data class LoginUiState(
    val account: String = "",
    val password: String = "",
    val isRegisterState: Boolean = false,
    val login: Boolean = false,
)