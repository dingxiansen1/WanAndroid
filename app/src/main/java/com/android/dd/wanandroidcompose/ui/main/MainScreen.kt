package com.android.dd.wanandroidcompose.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.dd.wanandroidcompose.ui.author.AuthorRoute
import com.android.dd.wanandroidcompose.ui.home.HomeMainScreen
import com.android.dd.wanandroidcompose.ui.mine.MineScreen
import com.android.dd.wanandroidcompose.ui.navigator.NavigatorMainScreen
import com.android.dd.wanandroidcompose.ui.project.ProjectRoute

@Composable
fun MainRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    val items = viewModel.mainNavigationBarItem

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        items = items,
        uiState = uiState,
        showToast = viewModel::showToast,
        updateSelectIndex = viewModel::updateSelectIndex,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    items: List<NavigationBarItemData>,
    uiState: MainUiState,
    showToast: (String) -> Unit,
    updateSelectIndex: (Int) -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.icon.name,
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.label)
                            )
                        },
                        selected = uiState.selectIndex == index,
                        onClick = { updateSelectIndex.invoke(index) }
                    )
                }
            }
        },
    ) {
        // 此处需要编写主界面
        Box(modifier = Modifier.padding(it)) {
            when (uiState.selectIndex) {
                0 -> HomeMainScreen() {
                    showToast.invoke(it)
                }
                1 -> ProjectRoute(
                    showToast = showToast::invoke
                )
                2 -> AuthorRoute(
                    showToast = showToast::invoke
                )
                3 -> NavigatorMainScreen()
                4 -> MineScreen() {
                    showToast.invoke(it)
                }
                else -> HomeMainScreen() {
                    showToast.invoke(it)
                }
            }
        }
    }
}

