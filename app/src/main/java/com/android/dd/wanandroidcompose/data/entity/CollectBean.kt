package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * 收藏
 */
@Keep
@Serializable
@Entity(
    tableName = "collect_article"
)
data class CollectBean(
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int,
    val author: String = "",
    val chapterId: Int = 0,
    val chapterName: String = "",
    val courseId: Int = 0,
    val desc: String = "",
    val envelopePic: String = "",
    val id: Int = 0,
    val link: String = "",
    val niceDate: String = "",
    val origin: String = "",
    val originId: Int = 0,
    val publishTime: Long = 0,
    val title: String = "",
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
){
    /**
     * 是否收藏状态
     */
    var collect: Boolean = true

    fun titleReplace(): String {
        return title.replace("&mdash;", "-").replace("&middot;", ",")
    }
}