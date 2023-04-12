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
import com.android.dd.wanandroidcompose.ui.author.AuthorScreen
import com.android.dd.wanandroidcompose.ui.home.HomeMainScreen
import com.android.dd.wanandroidcompose.ui.mine.MineScreen
import com.android.dd.wanandroidcompose.ui.navigator.NavigatorMainScreen
import com.android.dd.wanandroidcompose.ui.project.ProjectScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val items = viewModel.mainNavigationBarItem

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.toastMsg) {
        if (uiState.toastMsg.showToast && uiState.toastMsg.msg.isNotEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    uiState.toastMsg.msg
                )
            }
            viewModel.closeToast()
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
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
                        onClick = { viewModel.updateSelectIndex(index) }
                    )
                }
            }
        },
    ) {
        // 此处需要编写主界面
        Box(modifier = Modifier.padding(it)) {
            when (uiState.selectIndex) {
                0 -> HomeMainScreen() {
                    viewModel.showToast(it)
                }
                1 -> ProjectScreen() {
                    viewModel.showToast(it)
                }
                2 -> AuthorScreen() {
                    viewModel.showToast(it)
                }
                3 -> NavigatorMainScreen()
                4 -> MineScreen() {
                    viewModel.showToast(it)
                }
                else -> HomeMainScreen() {
                    viewModel.showToast(it)
                }
            }
        }
    }
}

