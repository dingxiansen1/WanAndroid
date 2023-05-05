package com.android.dd.wanandroidcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.AccountManager
import com.android.dd.wanandroidcompose.data.AccountState
import com.android.dd.wanandroidcompose.ui.collection.CollectionScreen
import com.android.dd.wanandroidcompose.ui.history.HistoryScreen
import com.android.dd.wanandroidcompose.ui.login.navigation.loginScreen
import com.android.dd.wanandroidcompose.ui.main.navigation.MainNavigationRoute
import com.android.dd.wanandroidcompose.ui.main.navigation.mainScreen
import com.android.dd.wanandroidcompose.ui.message.MessageScreen
import com.android.dd.wanandroidcompose.ui.navigator.series.desc.SeriesDescScreen
import com.android.dd.wanandroidcompose.ui.navigator.tutorial.desc.TutorialDescScreen
import com.android.dd.wanandroidcompose.ui.search.result.navigation.searchResultScreen
import com.android.dd.wanandroidcompose.ui.search.navigation.searchScreen
import com.android.dd.wanandroidcompose.ui.setting.SettingScreen
import com.android.dd.wanandroidcompose.ui.tool.ToolScreen
import com.android.dd.wanandroidcompose.ui.web.WebScreen
import com.android.dd.wanandroidcompose.utils.net.NetworkUtils
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.theme.DefaultTheme
import com.dd.basiccompose.theme.navigation.themeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //向android 12向下兼容
        installSplashScreen()

        // 更新用户状态
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountState.collect {
                    AccountManager.isLogin = it is AccountState.LogIn
                }
            }
        }

        setContent {
            //网络状态
            val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = isOffline) {
                NetworkUtils.isOnline = isOffline
            }

            DefaultTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        WanNavHost()
                    }
                }
            }
        }
    }

    @Composable
    private fun WanNavHost() {
        val navController = LocalNavController.current
        NavHost(
            navController = navController,
            startDestination = MainNavigationRoute
        ) {
            //主页面
            mainScreen()

            loginScreen()

            //消息中心
            composable(RouteName.Setting) {
                SettingScreen()
            }
            //收藏
            composable(RouteName.Collection) {
                CollectionScreen()
            }
            //主题
            themeScreen()
            //历史
            composable(RouteName.History) {
                HistoryScreen()
            }
            //工具列表
            composable(RouteName.Tool) {
                ToolScreen()
            }
            //消息中心
            composable(RouteName.Message) {
                MessageScreen()
            }
            //搜索
            searchScreen()
            //搜索结果
            searchResultScreen()
            //Web页面
            composable(
                route = RouteName.Web + "?link={link}&title={title}",
                arguments = listOf(
                    navArgument("link") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType })
            ) {
                val link = it.arguments?.getString("link") ?: "https://www.wanandroid.com"
                val title = it.arguments?.getString("title")
                WebScreen(link, title)
            }
            //体系详情页面
            composable(
                RouteName.SeriesDesc + "?cid={cid}",
                arguments = listOf(
                    navArgument(RouteName.Arguments.cid) { type = NavType.IntType },
                )
            ) {
                SeriesDescScreen()
            }
            //教程详情页面
            composable(
                RouteName.TutorialDesc + "?cid={cid}",
                arguments = listOf(
                    navArgument(RouteName.Arguments.cid) { type = NavType.IntType },
                )
            ) {
                TutorialDescScreen()
            }
        }
    }


}
