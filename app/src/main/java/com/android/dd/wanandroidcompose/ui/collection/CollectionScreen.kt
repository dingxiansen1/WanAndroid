package com.android.dd.wanandroidcompose.ui.collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.CollectBean
import com.android.dd.wanandroidcompose.widget.EmptyItem
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList
import com.dd.basiccompose.widget.DefaultTopBarBack
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = uiState.pagingData.collectAsLazyPagingItems()
    val showFloatingActionButton by remember {
        derivedStateOf { uiState.lazyListState.firstVisibleItemIndex > 10 }
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            DefaultTopBarBack(title = stringResource(id = R.string.collection))
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
                emptyView = {
                    EmptyItem()
                },
            ) {
                itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->
                    Box(modifier = Modifier.animateItemPlacement()) {
                        CollectionItem(
                            item!!,
                            onClick = {
                                nav.go(RouteName.webArguments(item.link, item.title))
                            },
                            collectOnClick = {
                                viewModel.collection(item)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CollectionItem(item: CollectBean, onClick: (() -> Unit), collectOnClick: (() -> Unit)) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke() }) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = item.titleReplace(),
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.author,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "时间:${item.niceDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            IconButton(onClick = {
                collectOnClick.invoke()
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "收藏",
                    tint = Color.Red,
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
    }
}