package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class Classify(
    val author: String = "",
    val children: List<Classify> = emptyList(),
    val courseId: Int = 0,
    val cover: String = "",
    val desc: String = "",
    val id: Int = 0,
    val lisense: String = "",
    val lisenseLink: String = "",
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)