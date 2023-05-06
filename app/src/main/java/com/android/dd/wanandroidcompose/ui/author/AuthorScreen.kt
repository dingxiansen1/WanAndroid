package com.android.dd.wanandroidcompose.ui.author

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.ExperimentalFoundationApi
import com.android.dd.wanandroidcompose.ui.author.child.AuthorChileScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorScreen(
    viewModel: AuthorViewModel = hiltViewModel(),
    showToast: (String) -> Unit = {},
) {
    val titles by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (titles.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(text = {
                        Text(
                            text = title.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // 迁移页面
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        })
                }
            }

            HorizontalPager(
                pageCount = titles.size, state = pagerState,
                // Add 16.dp padding to 'center' the pages
                contentPadding = PaddingValues(0.dp), modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                key = {
                    titles[it].id
                }
            ) { page ->
                AuthorChileScreen(titles[page].id){
                    showToast.invoke(it)
                }
            }
        }
    }
}

