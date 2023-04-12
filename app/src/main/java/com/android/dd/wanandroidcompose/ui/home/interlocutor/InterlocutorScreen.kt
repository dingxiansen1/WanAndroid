package com.android.dd.wanandroidcompose.ui.home.interlocutor

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.ToastData
import com.android.dd.wanandroidcompose.ui.home.main.ArticleItem
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterlocutorScreen(
    viewModel: InterlocutorViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
    showToast: (ToastData) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = uiState.pagingData.collectAsLazyPagingItems()
    val showFloatingActionButton by remember {
        derivedStateOf { uiState.lazyListState.firstVisibleItemIndex > 10 }
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        floatingActionButton = {
            if (showFloatingActionButton) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        uiState.lazyListState.animateScrollToItem(0)
                    }
                }) {
                    Icon(
                        Icons.Outlined.ArrowUpward,
                        Icons.Outlined.ArrowUpward.name,
                    )
                }
            }
        }
    ) {
        DefaultList(
            modifier = Modifier.padding(it),
            lazyPagingItems = pagingData,
            lazyListState = uiState.lazyListState,
        ) {
            itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->
                ArticleItem(
                    item!!,
                    onClick = {
                        nav.go(RouteName.webArguments(item.link, item.title))
                    },
                    collectOnClick = {
                        if (AccountManager.isLogin) {
                            viewModel.collection(item)
                        } else {
                            showToast.invoke(ToastData(true, "请先登录~"))
                        }
                    }
                )
            }
        }
    }
}
