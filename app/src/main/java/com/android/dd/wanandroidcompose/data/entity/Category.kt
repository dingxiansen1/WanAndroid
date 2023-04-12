package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import androidx.room.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Category(
    @SerialName("author")
    var author: String = "",
    @SerialName("courseId")
    var courseId: Int = -1,
    @SerialName("cover")
    var cover: String = "",
    @SerialName("desc")
    var desc: String = "",
    @SerialName("id")
    var id: Int = -1,
    @SerialName("lisense")
    var lisense: String = "",
    @SerialName("lisenseLink")
    var lisenseLink: String = "",
    @SerialName("name")
    var name: String = "",
    @SerialName("order")
    var order: Int = -1,
    @SerialName("parentChapterId")
    var parentChapterId: Int = 1,
    @SerialName("type")
    var type: Int = 1,
    @SerialName("userControlSetTop")
    var userControlSetTop: Boolean = false,
    @SerialName("visible")
    var visible: Int = -1,
)
