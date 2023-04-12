package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * 体系
 */
@Serializable
@Keep
data class Series(
    val author: String,
    val children: List<Classify>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

