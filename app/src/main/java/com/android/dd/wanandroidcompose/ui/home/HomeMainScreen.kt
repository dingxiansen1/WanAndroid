package com.android.dd.wanandroidcompose.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.ui.home.interlocutor.InterlocutorScreen
import com.android.dd.wanandroidcompose.ui.home.main.HomeScreen
import com.android.dd.wanandroidcompose.ui.home.square.SquareScreen
import com.android.dd.wanandroidcompose.ui.search.navigation.navigateToSearch
import com.dd.basiccompose.controller.LocalNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMainScreen(
    nav: NavHostController = LocalNavController.current,
    showToast: (String) -> Unit = {},
) {
    val pagerState = rememberPagerState(1)
    val tabTitle = arrayListOf(R.string.square, R.string.home, R.string.interlocution)
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
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                onClick = {
                    nav.navigateToSearch()
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = Icons.Outlined.Search.name
                )
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
                0 -> SquareScreen() {
                    showToast.invoke(it)
                }
                1 -> HomeScreen() {
                    showToast.invoke(it)
                }
                2 -> InterlocutorScreen() {
                    showToast.invoke(it)
                }
            }
        }
    }

}
