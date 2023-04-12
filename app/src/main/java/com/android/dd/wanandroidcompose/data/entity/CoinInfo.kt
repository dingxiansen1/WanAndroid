package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * 积分信息
 */
@Serializable
@Keep
data class CoinInfo(
    val coinCount: Int = 0,
    val rank: String = "",
    val level: Int = 0,
    val userId: Int = 0,
    val nickname: String = "",
    val username: String = ""
)