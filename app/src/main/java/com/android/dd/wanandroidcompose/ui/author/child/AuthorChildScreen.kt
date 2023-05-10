package com.android.dd.wanandroidcompose.ui.author.child

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.home.main.ArticleItem
import com.dd.basiccompose.widget.DefaultList


@Composable
fun AuthorChileScreen(
    pagingData: LazyPagingItems<Article>,
    onItemClick: ((String, String) -> Unit),
    onCollectItem: ((Article) -> Unit)
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DefaultList(
            lazyPagingItems = pagingData,
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->

                item?.run {
                    ArticleItem(
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
