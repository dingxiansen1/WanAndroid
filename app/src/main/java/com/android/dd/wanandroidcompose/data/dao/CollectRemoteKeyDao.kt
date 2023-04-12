package com.android.dd.wanandroidcompose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.dd.wanandroidcompose.data.entity.CollectRemoteKey

@Dao
interface CollectRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(remoteKey: CollectRemoteKey)

    @Query("SELECT * FROM collect_remote_key")
    suspend fun remoteKeysArticleId(): CollectRemoteKey?

    @Query("DELETE FROM collect_remote_key")
    suspend fun clearRemoteKeys()
}