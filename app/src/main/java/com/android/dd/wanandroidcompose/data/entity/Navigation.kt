package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * 导航
 */
@Serializable
@Keep
data class Navigation(
    var articles: List<Article> = emptyList(),
    var cid: Int = -1,
    var name: String = ""
)