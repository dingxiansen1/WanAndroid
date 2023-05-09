package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.NavigatorTab
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class NavigatorTabSerializer  @Inject constructor(): Serializer<NavigatorTab> {
    override val defaultValue = NavigatorTab()

    override suspend fun readFrom(input: InputStream): NavigatorTab {
        try {
            return JsonUtils.JSON.decodeFromString(
                NavigatorTab.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: NavigatorTab, output: OutputStream) {
        output.write(
            JsonUtils.JSON.encodeToString(NavigatorTab.serializer(), t).encodeToByteArray()
        )
    }
}