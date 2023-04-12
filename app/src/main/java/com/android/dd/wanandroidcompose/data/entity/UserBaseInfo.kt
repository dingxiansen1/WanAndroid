package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

/**
 * 个人基本信息
 */
@Keep
@kotlinx.serialization.Serializable
data class UserBaseInfo(
    val coinInfo: CoinInfo = CoinInfo(),
    val collectArticleInfo: CollectArticleInfo = CollectArticleInfo(),
    val userInfo: User = User()
)