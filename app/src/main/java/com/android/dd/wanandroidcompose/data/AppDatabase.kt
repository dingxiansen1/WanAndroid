package com.android.dd.wanandroidcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.dd.wanandroidcompose.data.dao.*
import com.android.dd.wanandroidcompose.data.entity.*
import com.dd.utils.Utils


val appRoom by lazy {
    AppDatabase.createDatabase(Utils.getApp())
}


@Database(
    version = 1,
    exportSchema = true,
    entities = [Article::class, RemoteKey::class, SearchHistory::class, CollectBean::class, CollectRemoteKey::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: ArticleDao
    abstract val remoteKeyDao: RemoteKeyDao
    abstract val searchHistoryDao: SearchHistoryDao
    abstract val collectRemoteKeyDao: CollectRemoteKeyDao
    abstract val collectionDao: CollectionDao

    companion object {

        private const val DATABASE_NAME = "wanAndroid.db"


        fun createDatabase(context: Context) = Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

}