package com.android.dd.wanandroidcompose.ui.navigator.navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun NavigatorScreen(
    viewModel: NavigatorViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState by viewModel.lazyListState.collectAsStateWithLifecycle()
    val select by derivedStateOf { lazyListState.firstVisibleItemIndex }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .verticalScroll(scrollState),
                maxItemsInEachRow = 1,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                uiState.forEachIndexed { index, navigation ->
                    Surface(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                scope.launch {
                                    lazyListState.scrollToItem(index)
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = if (select == index) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (select == index) {
                            MaterialTheme.colorScheme.onPrimary
                        } else MaterialTheme.colorScheme.onPrimaryContainer,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp)
                                    .align(Alignment.Center),
                                text = navigation.name,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                uiState.forEachIndexed { index, navigation ->
                    stickyHeader(key = navigation.name) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = navigation.name,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }
                    item(key = navigation.cid) {
                        FlowRow(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            navigation.articles.forEach {
                                Surface(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 8.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {
                                            if (it.link.isNotEmpty()) {
                                                nav.navigateToWeb(it.link, it.title)
                                            }
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ) {
                                    Text(
                                        text = it.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
