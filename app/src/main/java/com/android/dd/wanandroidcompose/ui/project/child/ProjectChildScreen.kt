package com.android.dd.wanandroidcompose.ui.project.child

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.data.entity.Article
import com.dd.basiccompose.ext.rememberLazyListState
import com.dd.basiccompose.widget.DefaultList


@Composable
internal fun ProjectChileScreen(
    pagingData: LazyPagingItems<Article>,
    onItemClick: ((String, String) -> Unit),
    onCollectItem: ((Article) -> Unit)
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DefaultList(
            lazyPagingItems = pagingData,
            lazyListState = pagingData.rememberLazyListState(),
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(pagingData, { index, item -> item.id }) { index, item ->
                item?.run {
                    ArticleProjectItem(
                        item,
                        onItemClick = {
                            onItemClick.invoke(item.link, item.title)
                        },
                        onCollectItem = {
                            onCollectItem.invoke(item)
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun ArticleProjectItem(article: Article, onItemClick: (() -> Unit), onCollectItem: (() -> Unit)) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick.invoke() }) {
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
                        onCollectItem.invoke()
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
