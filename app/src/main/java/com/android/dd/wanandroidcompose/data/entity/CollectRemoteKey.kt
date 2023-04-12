package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(
    tableName = "collect_remote_key",
)
data class CollectRemoteKey(
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int,
    val nextKey: Int?
)