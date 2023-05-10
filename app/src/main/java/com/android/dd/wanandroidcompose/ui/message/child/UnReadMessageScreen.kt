package com.android.dd.wanandroidcompose.ui.message.child

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.MsgBean
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.android.dd.wanandroidcompose.widget.EmptyItem
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList


@Composable
fun UnReadMessageScreen(
    pagingData: LazyPagingItems<MsgBean>,
    lazyListState: LazyListState,
    nav: NavHostController = LocalNavController.current,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        DefaultList(
            lazyPagingItems = pagingData,
            lazyListState = lazyListState,
            emptyView = {
                EmptyItem()
            },
        ) {
            itemsIndexed(pagingData, { index, item -> item }) { index, item ->
                item?.let {
                    MessageItem(item) {
                        nav.navigateToWeb(item.fullLink, "")
                    }
                }
            }
        }
    }
}