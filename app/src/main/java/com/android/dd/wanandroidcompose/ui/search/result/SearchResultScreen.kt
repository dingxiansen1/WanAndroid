package com.android.dd.wanandroidcompose.ui.search.result

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.ui.home.main.ArticleItem
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.ext.clickableNoRipple
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList
import com.dd.basiccompose.widget.SearchBar
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    viewModel: SearchResultViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = uiState.pagingData.collectAsLazyPagingItems()
    val showFloatingActionButton by remember {
        derivedStateOf { uiState.lazyListState.firstVisibleItemIndex > 10 }
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.toastMsg.collect {
            snackbarHostState.showSnackbar(
                it
            )
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    nav.navigateUp()
                }) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        Icons.Outlined.ArrowBack.name,
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.7f)
                ) {
                    SearchBar(text = uiState.searchKey, onTextChanged = {
                        viewModel.updateSearchKey(it)
                    })
                }
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickableNoRipple {
                            viewModel.search()
                        }
                )
            }
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
        Column(
            modifier = Modifier.padding(it),
        ) {
            DefaultList(
                lazyPagingItems = pagingData,
                lazyListState = uiState.lazyListState,
            ) {
                itemsIndexed(pagingData, { index, item -> item.id }) { index, item ->
                    ArticleItem(
                        item!!,
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