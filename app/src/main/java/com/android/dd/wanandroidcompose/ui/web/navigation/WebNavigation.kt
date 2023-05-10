package com.android.dd.wanandroidcompose.ui.web.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.android.dd.wanandroidcompose.ui.web.WebRoute


const val WebNavigationRoute = "web_route"
const val link = "link"
const val title = "title"


fun NavController.navigateToWeb(link: String, title: String, navOptions: NavOptions? = null) {
    this.navigate(WebNavigationRoute + "?link=${link}&title=${title}", navOptions)
}

fun NavGraphBuilder.webScreen() {
    composable(
        route = "$WebNavigationRoute?$link={link}&$title={title}",
        arguments = listOf(
            navArgument(link) { type = NavType.StringType },
            navArgument(title) { type = NavType.StringType })
    ) {
        val link = it.arguments?.getString(link) ?: "https://www.wanandroid.com"
        val title = it.arguments?.getString(title)
        WebRoute(link, title)
    }
}
