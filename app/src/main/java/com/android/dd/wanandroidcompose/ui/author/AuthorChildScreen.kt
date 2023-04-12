package com.android.dd.wanandroidcompose.ui.author

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.ToastData
import com.android.dd.wanandroidcompose.ui.home.main.ArticleItem
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultList


@Composable
fun AuthorChileScreen(
    cid: Int,
    viewModel: WechatChildViewModel = hiltViewModel(key = cid.toString()),
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
                lazyListState = uiState.listState,
            ) {
                itemsIndexed(pagingData, { index, item -> item.databaseId }) { index, item ->
                    ArticleItem(
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
