package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep


/**
 * 用户信息
 */
@Keep
@kotlinx.serialization.Serializable
data class User(
    val admin: Boolean = false,
    val chapterTops: List<String> = emptyList(),
    val collectIds: MutableList<String> = mutableListOf(),
    val email: String = "",
    val icon: String = "",
    val id: String = "",
    val nickname: String = "",
    val password: String = "",
    val token: String = "",
    val type: Int = 0,
    val username: String = ""
)