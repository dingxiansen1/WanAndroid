package com.android.dd.wanandroidcompose.ui.mine

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.AccountState
import com.android.dd.wanandroidcompose.data.isLogin
import com.android.dd.wanandroidcompose.ui.login.navigation.navigateToLogin
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.ext.clickableNoRipple
import com.dd.basiccompose.navigation.go

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MineScreen(
    viewModel: MineViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
    showToast: (String) -> Unit = {},
) {
    val accountState by viewModel.accountState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            //顶部按钮
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                IconButton(onClick = {
                    nav.go(RouteName.Setting)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = Icons.Outlined.Settings.name
                    )
                }
            }
            //用户头像
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickableNoRipple {
                        if (!accountState.isLogin) {
                            nav.navigateToLogin()
                        }
                    }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 10.dp)
                        .size(80.dp)
                        .background(Color(0x22000000), CircleShape)
                        .clip(CircleShape),
                    model = R.mipmap.default_account_picture,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = if (accountState.isLogin) (accountState as AccountState.LogIn).user.nickname else "未登录",
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (accountState.isLogin) {
                        Text(
                            text = "id: ${(accountState as AccountState.LogIn).user.id}",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {

                    MineRowItem(
                        Icons.Outlined.Message,
                        stringResource(id = R.string.message)
                    ) {
                        if (AccountManager.isLogin) {
                            nav.go(RouteName.Message)
                        } else {
                            showToast.invoke("请先登录~")
                        }
                    }

                    MineRowItem(
                        Icons.Outlined.Collections,
                        stringResource(id = R.string.collection)
                    ) {
                        if (AccountManager.isLogin) {
                            nav.go(RouteName.Collection)
                        } else {
                            showToast.invoke("请先登录~")
                        }
                    }
                    MineRowItem(
                        Icons.Outlined.History,
                        stringResource(id = R.string.browsing_history)
                    ) {
                        nav.go(RouteName.History)
                    }
                    MineRowItem(
                        Icons.Outlined.Handyman,
                        stringResource(id = R.string.tool_list)
                    ) {
                        nav.go(RouteName.Tool)
                    }
                }
            }


            Surface(
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 10.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    MineColumnItem(
                        Icons.Outlined.Cloud,
                        stringResource(id = R.string.project_address)
                    ) {
                        nav.navigateToWeb("https://github.com/dingxiansen1/WanAndroid","项目地址")
                    }

                    MineColumnItem(
                        Icons.Outlined.Settings,
                        stringResource(id = R.string.setting)
                    ) {
                        nav.go(RouteName.Setting)
                    }

                }
            }
        }
    }
}

@Composable
fun MineColumnItem(imageVector: ImageVector, stringResource: String, function: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickableNoRipple {
                function.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = imageVector.name,
                modifier = Modifier
                    .size(24.dp),
            )
            Text(
                text = stringResource,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = imageVector.name,
            modifier = Modifier
                .size(24.dp),
        )
    }
}

@Composable
fun RowScope.MineRowItem(
    imageVector: ImageVector,
    text: String,
    clickable: () -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable {
                clickable.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageVector.name,
            modifier = Modifier
                .size(36.dp)
                .padding(top = 16.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .size(36.dp)
                .padding(bottom = 16.dp)
        )
    }
}



