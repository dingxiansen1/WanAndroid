package com.android.dd.wanandroidcompose.ui.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.ui.navigator.navigator.NavigatorScreen
import com.android.dd.wanandroidcompose.ui.navigator.series.SeriesScreen
import com.android.dd.wanandroidcompose.ui.navigator.tutorial.TutorialScreen
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.ExperimentalFoundationApi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigatorMainScreen() {
    val pagerState = rememberPagerState(0)
    val tabTitle = arrayListOf(R.string.navigator, R.string.series, R.string.tutorial)
    val coroutineScope = rememberCoroutineScope()
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TabRow(
                modifier = Modifier
                    .padding(start = 100.dp, end = 100.dp)
                    .align(Alignment.Center),
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ) {
                tabTitle.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = stringResource(id = title)) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // 迁移页面
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
        HorizontalPager(
            pageCount = tabTitle.size,
            state = pagerState,
            // Add 16.dp padding to 'center' the pages
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> NavigatorScreen()
                1 -> SeriesScreen()
                2 -> TutorialScreen()
            }
        }
    }
}
