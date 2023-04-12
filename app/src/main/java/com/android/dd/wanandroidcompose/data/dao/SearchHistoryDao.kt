package com.android.dd.wanandroidcompose.data.dao

import androidx.room.*
import com.android.dd.wanandroidcompose.data.entity.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(searchHistory: SearchHistory)

    @Query("SELECT * FROM searchHistory order by time desc")
    fun querySearchHistory(): Flow<List<SearchHistory>>

    @Delete
    suspend fun del(searchHistory: SearchHistory)

}