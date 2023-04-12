package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.ProjectTab
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream

val Context.projectTab by dataStore(Constant.ProjectTab, serializer = ProjectTabSerializer)

object ProjectTabSerializer : Serializer<ProjectTab> {
    override val defaultValue = ProjectTab()

    override suspend fun readFrom(input: InputStream): ProjectTab {
        try {
            return JsonUtils.JSON.decodeFromString(
                ProjectTab.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: ProjectTab, output: OutputStream) {
        output.write(JsonUtils.JSON.encodeToString(ProjectTab.serializer(), t).encodeToByteArray())
    }
}