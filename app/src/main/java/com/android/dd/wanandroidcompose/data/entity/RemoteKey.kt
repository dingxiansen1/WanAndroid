package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(
    tableName = "remote_key",
    indices = [
        //用于普通列表
        Index(value = ["articleType"], unique = true),
    ]
)
data class RemoteKey(
    @PrimaryKey
    val articleType: Int,
    val nextKey: Int?
)