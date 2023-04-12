package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Keep
@Entity(
    tableName = "searchHistory",
)
data class SearchHistory(
    @PrimaryKey
    val key: String,
    val time: Long = System.currentTimeMillis(),
)
