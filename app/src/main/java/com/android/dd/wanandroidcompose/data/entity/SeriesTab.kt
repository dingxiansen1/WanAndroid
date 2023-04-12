package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class SeriesTab(
    val seriesTab:List<Series> = emptyList(),
)