package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class BasicBean<T>(
    var data: T?,
    var errorCode: Int,
    var errorMsg: String
)