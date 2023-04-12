package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

/**
 * 收藏文章信息
 */
@Keep
@kotlinx.serialization.Serializable
data class CollectArticleInfo(
    val count: Int = 0
)