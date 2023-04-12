package com.android.dd.wanandroidcompose.ui.navigator.series

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel = hiltViewModel(),
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
                uiState.forEachIndexed { index, series ->
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
                                text = series.name,
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
                uiState.forEachIndexed { index, series ->
                    stickyHeader(key = series.name) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background),
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = series.name,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }
                    item(key = series.name + "item") {
                        FlowRow(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            series.children.forEach {
                                Surface(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 8.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable {
                                            nav.go(RouteName.seriesDescArguments(it.id))
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ) {
                                    Text(
                                        text = it.name,
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
