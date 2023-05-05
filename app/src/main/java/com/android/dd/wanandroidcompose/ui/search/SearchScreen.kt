package com.android.dd.wanandroidcompose.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.ui.search.result.navigation.navigateToSearchResult
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.ext.clickableNoRipple
import com.dd.basiccompose.widget.SearchBar


@Composable
internal fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchKey by viewModel.searchKey.collectAsStateWithLifecycle()
    SearchScreen(
        uiState = uiState,
        searchKey = searchKey,
        navigateUp = nav::navigateUp,
        updateSearchKey = viewModel::updateSearchKey,
        addSearchHistory = viewModel::addSearchHistory,
        navigateToSearchResult = nav::navigateToSearchResult,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun SearchScreen(
    uiState: SearchUiState,
    searchKey: String,
    navigateUp: () -> Unit,
    updateSearchKey: (String) -> Unit,
    addSearchHistory: (String) -> Unit,
    navigateToSearchResult: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navigateUp.invoke()
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
                    SearchBar(text = searchKey, onTextChanged = {
                        updateSearchKey.invoke(it)
                    })
                }
                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickableNoRipple {
                            addSearchHistory.invoke(searchKey)
                            navigateToSearchResult.invoke(searchKey)
                        }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            if (uiState.hotKey.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.hotSearch),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    uiState.hotKey.forEachIndexed { index, hotKey ->
                        Surface(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    addSearchHistory.invoke(hotKey.name)
                                    navigateToSearchResult.invoke(hotKey.name)
                                },
                            shape = RoundedCornerShape(16.dp),
                            color =
                            MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    text = hotKey.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                    }
                }
            }

            if (uiState.searchHistory.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.searchHistory),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    uiState.searchHistory.forEachIndexed { index, key ->
                        Surface(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    addSearchHistory.invoke(key.key)
                                    navigateToSearchResult.invoke(key.key)
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    text = key.key,
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}