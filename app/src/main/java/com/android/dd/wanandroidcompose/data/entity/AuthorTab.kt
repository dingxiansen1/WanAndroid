package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class AuthorTab(
    val authorTab:List<Category> = emptyList(),
)