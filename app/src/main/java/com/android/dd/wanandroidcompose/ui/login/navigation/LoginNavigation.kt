package com.android.dd.wanandroidcompose.ui.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.dd.wanandroidcompose.ui.login.LoginRoute


const val LoginNavigationRoute = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(LoginNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen() {
    composable(route = LoginNavigationRoute) {
        LoginRoute()
    }
}
