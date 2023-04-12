package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.AuthorTab
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream

val Context.authorTab by dataStore(Constant.AuthorTab, serializer = AuthorTabSerializer)

object AuthorTabSerializer : Serializer<AuthorTab> {
    override val defaultValue = AuthorTab()

    override suspend fun readFrom(input: InputStream): AuthorTab {
        try {
            return JsonUtils.JSON.decodeFromString(
                AuthorTab.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: AuthorTab, output: OutputStream) {
        output.write(JsonUtils.JSON.encodeToString(AuthorTab.serializer(), t).encodeToByteArray())
    }
}