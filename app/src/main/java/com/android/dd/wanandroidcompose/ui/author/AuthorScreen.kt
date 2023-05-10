package com.android.dd.wanandroidcompose.ui.author

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.data.entity.Category
import com.android.dd.wanandroidcompose.ui.author.child.AuthorChileScreen
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AuthorRoute(
    viewModel: AuthorViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
    showToast: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    AuthorScreen(
        uiState = uiState,
        pagerState = pagerState,
        animateScrollToPage = {
            // 迁移页面
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        },
        pagingData = viewModel::getPaging,
        onItemClick = nav::navigateToWeb,
        onCollectItem = {
            if (AccountManager.isLogin) {
                viewModel.collection(it)
            } else {
                showToast.invoke("请先登录~")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorScreen(
    uiState: List<Category>,
    pagerState: PagerState,
    animateScrollToPage: (Int) -> Unit,
    pagingData: (Int) -> Flow<PagingData<Article>>,
    onItemClick: ((String, String) -> Unit),
    onCollectItem: ((Article) -> Unit)
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                uiState.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            animateScrollToPage.invoke(index)
                        })
                }
            }

            HorizontalPager(
                pageCount = uiState.size, state = pagerState,
                // Add 16.dp padding to 'center' the pages
                contentPadding = PaddingValues(0.dp), modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                key = {
                    uiState[it].id
                }
            ) { page ->
                AuthorChileScreen(
                    pagingData = pagingData.invoke(uiState[page].id)
                        .collectAsLazyPagingItems(),
                    onItemClick = onItemClick::invoke,
                    onCollectItem = onCollectItem::invoke,
                )
            }
        }
    }
}

