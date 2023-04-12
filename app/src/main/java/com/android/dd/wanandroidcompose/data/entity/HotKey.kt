package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * 热搜词
 */
@Serializable
@Keep
data class HotKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

@Serializable
@Keep
data class HotKeyList(
    val hotKey: List<HotKey> = emptyList()
)
