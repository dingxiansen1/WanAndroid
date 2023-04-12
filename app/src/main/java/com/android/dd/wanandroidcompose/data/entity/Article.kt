package com.android.dd.wanandroidcompose.data.entity

import android.text.TextUtils
import androidx.annotation.Keep
import androidx.room.*
import com.android.dd.wanandroidcompose.constant.RemoteKeyType
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString


@Keep
@Serializable
@TypeConverters(Article.Converters::class)
@Entity(
    tableName = "article",
    indices = [
        //用于项目,微信公众号 列表
        Index(value = ["articleType", "courseId"]),
        //用于普通列表
        Index(value = ["articleType"]),
    ]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int,
    //用于区分数据来源
    @SerialName("articleType")
    var articleType: Int = RemoteKeyType.Home,

    @SerialName("adminAdd")
    var adminAdd: Boolean = false,
    @SerialName("apkLink")
    var apkLink: String = "",
    @SerialName("audit")
    var audit: Int = 0,
    @SerialName("author")
    var author: String = "鸿洋",
    @SerialName("canEdit")
    var canEdit: Boolean = false,
    @SerialName("chapterId")
    var chapterId: Int = 0,
    @SerialName("chapterName")
    var chapterName: String = "",
    @SerialName("collect")
    var collect: Boolean = false,
    @SerialName("courseId")
    var courseId: Int = 0,
    @SerialName("desc")
    var desc: String = "最近看了好多MVI的文章，原理大多都是参照google发布的 应用架构指南，但是实现方式有很多种，就想自己封装一套自己喜欢用的MVI架构，以供以后开发App使用。\n" +
            "说干就干，准备对标“玩Android”，利用提供的数据接口，搭建一个自己习惯使用的一套App项目，项目地址：Github wanandroid。",
    @SerialName("descMd")
    var descMd: String = "",
    @SerialName("envelopePic")
    var envelopePic: String = "",
    @SerialName("fresh")
    var fresh: Boolean = false,
    @SerialName("host")
    var host: String = "",
    @SerialName("id")
    var id: Int,
    @SerialName("isAdminAdd")
    var adminAddtwo: Boolean = false,
    @SerialName("link")
    var link: String = "https://www.wanandroid.com/blogimgs/aa877cf7-a2a0-477b-bae7-619d714adf5b.png",
    @SerialName("niceDate")
    var niceDate: String = "2023-02-08 00:11",
    @SerialName("niceShareDate")
    var niceShareDate: String = "",
    @SerialName("origin")
    var origin: String = "",
    @SerialName("prefix")
    var prefix: String = "",
    @SerialName("projectLink")
    var projectLink: String = "",
    @SerialName("publishTime")
    var publishTime: Long = 0,
    @SerialName("realSuperChapterId")
    var realSuperChapterId: Int = 0,
    @SerialName("selfVisible")
    var selfVisible: Int = 0,
    @SerialName("shareDate")
    var shareDate: Long = 0,
    @SerialName("shareUser")
    var shareUser: String = "",
    @SerialName("superChapterId")
    var superChapterId: Int = 0,
    @SerialName("superChapterName")
    var superChapterName: String? = "广场",
    @SerialName("tags")
    var tags: List<Tag> = arrayListOf(),
    @SerialName("title")
    var title: String = "狂飙！Android 14 第一个预览版已发布～",
    @SerialName("type")
    var type: Int = 0,
    @SerialName("userId")
    var userId: Int = 0,
    @SerialName("visible")
    var visible: Int = 0,
    @SerialName("zan")
    var zan: Int = 0
) {
    @Keep
    @Serializable
    data class Tag(
        @SerialName("name")
        var name: String,
        @SerialName("url")
        var url: String
    )

    class Converters {
        @TypeConverter
        fun tagsToString(ruleSearch: List<Tag>): String =
            JsonUtils.JSON.encodeToString(ruleSearch)

        @TypeConverter
        fun stringToTags(json: String) =
            JsonUtils.JSON.decodeFromString<List<Tag>>(json)
    }

    fun authorOrShareUser(): String {
        if (!TextUtils.isEmpty(author)) {
            return "作者:$author"
        }
        return "分享人:$shareUser"
    }

    fun titleReplace(): String {
        return title.replace("&mdash;", "-").replace("&middot;", ",")
    }

    fun descReplace(): String {
        return desc.replace("&mdash;", "-").replace("&middot;", ",")
    }
}