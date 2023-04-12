package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class ProjectTab(
    val projectTab:List<Category> = emptyList(),
)