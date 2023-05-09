package com.android.dd.wanandroidcompose.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.android.dd.wanandroidcompose.data.entity.SeriesTab
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
class SeriesTabSerializer  @Inject constructor(): Serializer<SeriesTab> {
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