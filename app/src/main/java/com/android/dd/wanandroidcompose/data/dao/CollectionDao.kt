package com.android.dd.wanandroidcompose.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.android.dd.wanandroidcompose.data.entity.CollectBean

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(articleList: List<CollectBean>)

    @Query("SELECT * FROM collect_article")
    fun getPagingSource(): PagingSource<Int, CollectBean>

    @Delete
    suspend fun del(article: CollectBean)

    @Query("DELETE FROM collect_article")
    suspend fun delAll()
}