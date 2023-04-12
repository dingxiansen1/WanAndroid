package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * 消息
 */
@Serializable
@Keep
data class MsgBean(
    val category: Int,
    val date: Long,
    val fromUser: String,
    val fromUserId: Int,
    val fullLink: String,
    val id: Int,
    val isRead: Int,
    val link: String,
    val message: String,
    val niceDate: String,
    val tag: String,
    val title: String,
    val userId: Int
)