package com.android.dd.wanandroidcompose.ui.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.ui.home.main.ArticleItem
import com.android.dd.wanandroidcompose.ui.project.child.ArticleProjectItem
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.widget.DefaultList
import com.dd.basiccompose.widget.DefaultTopBarBack
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = uiState.pagingData.collectAsLazyPagingItems()
    val showFloatingActionButton by remember {
        derivedStateOf { uiState.lazyListState.firstVisibleItemIndex > 10 }
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopBarBack(title = stringResource(id = R.string.browsing_history))
        },
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
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            DefaultList(
                lazyPagingItems = pagingData,
                lazyListState = uiState.lazyListState,
            ) {
                itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->
                    item?.let {
                        if (item.envelopePic.isEmpty()) {
                            ArticleItem(
                                item,
                                onItemClick = {
                                    nav.navigateToWeb(item.link, item.title)
                                },
                                onCollectItem = {
                                    if (AccountManager.isLogin) {
                                        viewModel.collection(item)
                                    } else {
                                        viewModel.showToast("请先登录~")
                                    }
                                }
                            )
                        } else {
                            ArticleProjectItem(
                                item,
                                onItemClick = {
                                    nav.navigateToWeb(item.link, item.title)
                                },
                                onCollectItem = {
                                    if (AccountManager.isLogin) {
                                        viewModel.collection(item)
                                    } else {
                                        viewModel.showToast("请先登录~")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
