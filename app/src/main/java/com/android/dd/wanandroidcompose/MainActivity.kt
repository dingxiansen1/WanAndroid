package com.android.dd.wanandroidcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.android.dd.wanandroidcompose.manager.ErrorTextPrefix
import com.android.dd.wanandroidcompose.manager.Message
import com.android.dd.wanandroidcompose.manager.SnackbarManager
import com.android.dd.wanandroidcompose.manager.SnackbarState
import com.android.dd.wanandroidcompose.manager.UiText
import com.android.dd.wanandroidcompose.ui.collection.CollectionScreen
import com.android.dd.wanandroidcompose.ui.history.HistoryScreen
import com.android.dd.wanandroidcompose.ui.login.navigation.loginScreen
import com.android.dd.wanandroidcompose.ui.main.navigation.MainNavigationRoute
import com.android.dd.wanandroidcompose.ui.main.navigation.mainScreen
import com.android.dd.wanandroidcompose.ui.message.MessageScreen
import com.android.dd.wanandroidcompose.ui.navigator.series.desc.SeriesDescScreen
import com.android.dd.wanandroidcompose.ui.navigator.tutorial.desc.TutorialDescScreen
import com.android.dd.wanandroidcompose.ui.search.navigation.searchScreen
import com.android.dd.wanandroidcompose.ui.search.result.navigation.searchResultScreen
import com.android.dd.wanandroidcompose.ui.setting.SettingScreen
import com.android.dd.wanandroidcompose.ui.tool.ToolScreen
import com.android.dd.wanandroidcompose.ui.web.navigation.webScreen
import com.android.dd.wanandroidcompose.utils.net.NetworkUtils
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.theme.DefaultTheme
import com.dd.basiccompose.theme.navigation.themeScreen
import com.dd.basiccompose.widget.DefaultAppSnackbar
import com.dd.common.web.WebViewManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var exitTime = 0L

    private val viewModel: AppViewModel by viewModels()

    @Inject
    lateinit var snackbarManager: SnackbarManager

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
        //WebView预加载
        WebViewManager.prepare(applicationContext)
        //双击返回键回退桌面
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    val msg = getString(R.string.one_more_press_2_back)
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                } else {
                    moveTaskToBack(true)
                }
            }
        })
        setContent {
            DefaultTheme {
                WanApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun WanApp() {
        //网络状态
        val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = isOffline) {
            NetworkUtils.isOnline = isOffline
        }
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(key1 = snackbarHostState) {
            collectAndShowSnackbar(snackbarManager, snackbarHostState, context)
        }
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.systemBarsPadding(),
                    snackbar = { snackbarData ->
                        val isError =
                            snackbarData.visuals.message.startsWith(ErrorTextPrefix)
                        DefaultAppSnackbar(snackbarData, isError)
                    }
                )
            },
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

    override fun onDestroy() {
        super.onDestroy()
        WebViewManager.destroy()
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
        webScreen()
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

suspend fun collectAndShowSnackbar(
    snackbarManager: SnackbarManager,
    snackbarHostState: SnackbarHostState,
    context: Context,
) {
    snackbarManager.messages.collect { messages ->
        if (messages.isNotEmpty()) {
            val message = messages.first()
            val text = getMessageText(message, context)

            if (message.state == SnackbarState.Error) {
                snackbarHostState.showSnackbar(
                    message = ErrorTextPrefix + text,
                )
            } else {
                snackbarHostState.showSnackbar(message = text)
            }
            snackbarManager.setMessageShown(message.id)
        }
    }
}

fun getMessageText(message: Message, context: Context): String {
    return when (message.uiText) {
        is UiText.DynamicString -> (message.uiText as UiText.DynamicString).value
        is UiText.StringResource -> context.getString(
            (message.uiText as UiText.StringResource).resId,
            *(message.uiText as UiText.StringResource).args.map { it.toString(context) }
                .toTypedArray()
        )
    }
}

