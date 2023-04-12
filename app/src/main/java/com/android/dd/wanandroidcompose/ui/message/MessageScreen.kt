package com.android.dd.wanandroidcompose.ui.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.ui.message.child.ReadMessageScreen
import com.android.dd.wanandroidcompose.ui.message.child.UnReadMessageScreen
import com.dd.basiccompose.widget.DefaultTopBarBack
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val unReadPagingData = uiState.unReadPagingData.collectAsLazyPagingItems()
    val readPagingData = uiState.readPagingData.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(0)
    val tabTitle = arrayListOf(R.string.new_message, R.string.un_read_message)
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        DefaultTopBarBack(title = stringResource(id = R.string.message))
    }) {
        Column(modifier = Modifier.padding(it)) {
            Box(
                modifier = Modifier.fillMaxWidth()
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
                        Tab(text = { Text(text = stringResource(id = title)) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                // 迁移页面
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            })
                    }
                }
            }
            HorizontalPager(
                pageCount = tabTitle.size, state = pagerState,
                // Add 16.dp padding to 'center' the pages
                contentPadding = PaddingValues(0.dp), modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> UnReadMessageScreen(
                        unReadPagingData, uiState.unReadLazyListState
                    )
                    1 -> ReadMessageScreen(
                        readPagingData, uiState.readLazyListState
                    )
                }
            }
        }
    }


}





