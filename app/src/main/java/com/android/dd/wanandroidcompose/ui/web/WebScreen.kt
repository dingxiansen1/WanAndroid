package com.android.dd.wanandroidcompose.ui.web

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.android.dd.wanandroidcompose.data.appRoom
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.widget.WebViewScreen

@Composable
fun WebScreen(
    webUrl: String,
    title: String?,
    navCtrl: NavHostController = LocalNavController.current,
) {
    var menuState by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(webUrl) {
        val article = appRoom.dao.queryByLink(webUrl)
        article?.let {
            //设为0视为未设置主键,就不会覆盖之前的，而是自增
            it.databaseId = 0
            it.articleType = RemoteKeyType.History
            appRoom.dao.add(it)
        }
    }
    WebViewScreen(webUrl = webUrl, title = title, navCtrl = navCtrl) {
        IconButton(onClick = {
            menuState = menuState.not()
        }) {
            Icon(
                imageVector = Icons.Outlined.MoreHoriz,
                contentDescription = Icons.Outlined.MoreHoriz.name,
            )
        }
        MoreDropdownMenu(menuState, {
            menuState = false
        }, {

        }, {

        })
    }
}

@Composable
fun MoreDropdownMenu(
    menuState: Boolean,
    function: () -> Unit,
    function1: () -> Unit,
    function2: () -> Unit
) {
    DropdownMenu(
        expanded = menuState,
        offset = DpOffset(0.dp, 0.dp),
        onDismissRequest = { function.invoke() },
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = Icons.Outlined.Share.name,
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.share),
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            onClick = {
                function1.invoke()
            },
        )
    }
}

