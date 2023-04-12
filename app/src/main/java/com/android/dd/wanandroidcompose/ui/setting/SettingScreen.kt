package com.android.dd.wanandroidcompose.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.data.isLogin
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.theme.DarkMode
import com.dd.basiccompose.theme.ThemeUtils
import com.dd.basiccompose.theme.navigation.navigateToTheme
import com.dd.basiccompose.widget.DefaultTopBarBack
import com.dd.basiccompose.widget.item.SettingItem
import com.dd.basiccompose.widget.item.SettingSwitch
import com.dd.utils.ApplicationUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val accountState by viewModel.accountState.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            DefaultTopBarBack(title = stringResource(R.string.setting))
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier.padding(it)) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Text(
                text = stringResource(R.string.theme),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            SettingItem(
                title = stringResource(R.string.theme),
                description = stringResource(R.string.theme_tips),
                icon = Icons.Outlined.ColorLens,
            ) {
                nav.navigateToTheme()
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = stringResource(R.string.other),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            SettingItem(
                title = stringResource(R.string.version),
                description = stringResource(R.string.version_tips) + ApplicationUtils.appInfo.versionCode,
                icon = Icons.Outlined.BugReport,
            ) {

            }


            if (accountState.isLogin) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .width(160.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .align(Alignment.Center)
                            .clickable {
                                viewModel.logout()
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Red,
                        contentColor = Color.White,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp)
                                    .align(Alignment.Center),
                                text = stringResource(id = R.string.logout),
                                style = MaterialTheme.typography.titleMedium,
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