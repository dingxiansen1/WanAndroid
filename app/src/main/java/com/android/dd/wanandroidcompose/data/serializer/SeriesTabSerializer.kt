package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.SeriesTab
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream

val Context.seriesTab by dataStore(Constant.SeriesTab, serializer = SeriesTabSerializer)

object SeriesTabSerializer : Serializer<SeriesTab> {
    override val defaultValue = SeriesTab()

    override suspend fun readFrom(input: InputStream): SeriesTab {
        try {
            return JsonUtils.JSON.decodeFromString(
                SeriesTab.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: SeriesTab, output: OutputStream) {
        output.write(JsonUtils.JSON.encodeToString(SeriesTab.serializer(), t).encodeToByteArray())
    }
}