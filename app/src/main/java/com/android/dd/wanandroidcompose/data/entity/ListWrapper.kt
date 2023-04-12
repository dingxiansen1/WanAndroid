package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class ListWrapper<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<T>
)