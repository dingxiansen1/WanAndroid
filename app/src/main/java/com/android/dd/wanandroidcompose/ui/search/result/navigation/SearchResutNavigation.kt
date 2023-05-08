package com.android.dd.wanandroidcompose.ui.search.result.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.android.dd.wanandroidcompose.ui.search.result.SearchResultScreen


const val SearchResultNavigationRoute = "search_result_route"
const val arg = "key"

fun NavController.navigateToSearchResult(key: String, navOptions: NavOptions? = null) {
    this.navigate("$SearchResultNavigationRoute?key=$key", navOptions)
}

fun NavGraphBuilder.searchResultScreen() {
    composable(
        route = SearchResultNavigationRoute +"?key={key}",
        arguments = listOf(
            navArgument(key) { type = NavType.StringType },
        )
    ) {
        SearchResultScreen()
    }
}
