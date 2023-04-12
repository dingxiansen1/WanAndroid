package com.android.dd.wanandroidcompose.data.entity

import androidx.annotation.Keep
import com.dd.basiccompose.widget.BannerData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Keep
data class HomeBanner(
    @SerialName("desc")
    var desc: String,
    @SerialName("id")
    var id: Int,
    @SerialName("imagePath")
    var imagePath: String,
    @SerialName("isVisible")
    var isVisible: Int,
    @SerialName("order")
    var order: Int,
    @SerialName("title")
    var title: String,
    @SerialName("type")
    var type: Int,
    @SerialName("url")
    var url: String
): BannerData {
    override fun imageUrl(): String {
        return imagePath
    }

    override fun imageLink(): String {
        return url
    }

    override fun linkTitle(): String {
        return title
    }
}
