package com.android.dd.wanandroidcompose.ui.login

import com.android.dd.wanandroidcompose.net.HttpService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: HttpService
) {

    suspend fun login(account: String, password: String) =
        service.login(account, password)

    suspend fun register(account: String, password: String) =
        service.register(account, password, password)

    suspend fun logout() = service.logout()

    suspend fun getUserInfo() = service.getUserInfo()

}
