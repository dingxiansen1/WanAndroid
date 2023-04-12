package com.android.dd.wanandroidcompose.ui.home.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.ToastData
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.HomeBanner
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.Banner
import com.dd.basiccompose.widget.DefaultList
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
    showToast: (ToastData) -> Unit = {},
) {
    val banner by viewModel.banner.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsLazyPagingItems()
    val lazyListState by viewModel.lazyListState.collectAsStateWithLifecycle()
    val showFloatingActionButton by remember() {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 10 }
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
                        lazyListState.animateScrollToItem(0)
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
            lazyPagingItems = uiState,
            lazyListState = lazyListState,
        ) {
            item(key = "banner") {
                Box(modifier = Modifier.animateItemPlacement()) {
                    BannerItem(banner) { url, title ->
                        nav.go(RouteName.webArguments(url, title))
                    }
                }
            }
            itemsIndexed(uiState, { index, item -> item.databaseId }) { index, item ->
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

@Composable
fun BannerItem(banner: List<HomeBanner>, onClick: ((String, String) -> Unit)) {
    Banner(
        modifier = Modifier
            .background(Color.Unspecified)
            .padding(20.dp, 10.dp)
            .height(150.dp)
            .fillMaxWidth(),
        list = banner,
        onClick = { url, title ->
            onClick.invoke(url, title)
        }
    )
}


@Composable
fun ArticleItem(article: Article, onClick: (() -> Unit), collectOnClick: (() -> Unit)) {
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
                    text = article.titleReplace(),
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
                        text = article.authorOrShareUser(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "时间:${article.niceDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    article.superChapterName?.let {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "分类:${it}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            IconButton(onClick = {
                collectOnClick.invoke()
            }) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "收藏",
                    tint = if (article.collect) Color.Red else Color.DarkGray,
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

