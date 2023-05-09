package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.HotKeyList
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class HotKeyListSerializer  @Inject constructor(): Serializer<HotKeyList> {
    override val defaultValue = HotKeyList()

    override suspend fun readFrom(input: InputStream): HotKeyList {
        try {
            return JsonUtils.JSON.decodeFromString(
                HotKeyList.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: HotKeyList, output: OutputStream) {
        output.write(JsonUtils.JSON.encodeToString(HotKeyList.serializer(), t).encodeToByteArray())
    }
}