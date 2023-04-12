package com.android.dd.wanandroidcompose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.dd.wanandroidcompose.data.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_key WHERE articleType =:articleType")
    suspend fun remoteKeysArticleId(articleType: Int): RemoteKey?

    @Query("DELETE FROM remote_key WHERE articleType =:articleType")
    suspend fun clearRemoteKeys(articleType: Int)
}