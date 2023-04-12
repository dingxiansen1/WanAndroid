package com.android.dd.wanandroidcompose.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.android.dd.wanandroidcompose.data.entity.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(articleList: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(article: Article)


    @Query("SELECT * FROM article WHERE articleType =:articleType")
    fun getPagingSource(articleType: Int): PagingSource<Int, Article>

    @Query("SELECT * FROM article WHERE articleType =:articleType and chapterId = :cid")
    fun getPagingSourceByCid(articleType: Int, cid: Int): PagingSource<Int, Article>

    @Query("SELECT * FROM article WHERE link =:link")
    fun queryByLink(link: String): Article?

    @Delete()
    suspend fun del(article: Article)

    @Query("DELETE FROM article WHERE articleType=:articleType")
    suspend fun delAll(articleType: Int)

    @Update
    suspend fun update(article: Article)

    @Query("update article set collect = :collect where id = :id")
    suspend fun collect(id: Int, collect: Int)

}