package com.android.dd.wanandroidcompose.data.serializer

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.User
import com.dd.common.utils.JsonUtils
import kotlinx.serialization.SerializationException
import java.io.InputStream
import java.io.OutputStream

val Context.user by dataStore(Constant.User, serializer = UserSerializer)

object UserSerializer : Serializer<User> {
    override val defaultValue = User()

    override suspend fun readFrom(input: InputStream): User {
        try {
            return JsonUtils.JSON.decodeFromString(
                User.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: User, output: OutputStream) {
        output.write(JsonUtils.JSON.encodeToString(User.serializer(), t).encodeToByteArray())
    }
}