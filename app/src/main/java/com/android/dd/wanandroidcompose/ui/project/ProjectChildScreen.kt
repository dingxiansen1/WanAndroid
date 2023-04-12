package com.android.dd.wanandroidcompose.ui.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.ToastData
import com.android.dd.wanandroidcompose.data.entity.Article
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList


@Composable
fun ProjectChileScreen(
    cid: Int,
    viewModel: ProjectChildViewModel = hiltViewModel(key = cid.toString()),
    nav: NavHostController = LocalNavController.current,
    showToast: (ToastData) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = uiState.data?.collectAsLazyPagingItems()
    LaunchedEffect(key1 = cid) {
        if (pagingData == null) {
            viewModel.getPaging(cid)
        }
    }
    if (pagingData != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            DefaultList(
                lazyPagingItems = pagingData,
                lazyListState = uiState.lazyListState,
            ) {
                itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->
                    ArticleProjectItem(
                        item!!,
                        onClick = {
                            nav.go(RouteName.webArguments(item.link, item.title))
                        },
                        collectOnClick = {
                            if (AccountManager.isLogin){
                                viewModel.collection(item)
                            }else{
                                showToast.invoke(ToastData(true,"请先登录~"))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleProjectItem(article: Article, onClick: (() -> Unit), collectOnClick: (() -> Unit)) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke() }) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(90.dp)
                    .requiredHeight(150.dp),
                model = article.envelopePic,
                contentDescription = "项目图片"
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
            ) {
                Text(
                    text = article.titleReplace(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = article.descReplace(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "时间:${article.niceDate}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = article.authorOrShareUser(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
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
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
        )
    }
}
