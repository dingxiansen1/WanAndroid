package com.android.dd.wanandroidcompose.utils.net

import kotlinx.coroutines.flow.Flow

/**
 * 用于报告应用程序连接状态的实用程序
 */
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
