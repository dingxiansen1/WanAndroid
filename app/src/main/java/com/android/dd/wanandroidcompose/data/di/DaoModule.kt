package com.android.dd.wanandroidcompose.data.di
import com.android.dd.wanandroidcompose.data.AppDatabase
import com.android.dd.wanandroidcompose.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun providesArticleDao(
        appRoom: AppDatabase
    ): ArticleDao = appRoom.dao


    @Provides
    @Singleton
    fun providesRemoteKeyDao(
        appRoom: AppDatabase
    ): RemoteKeyDao = appRoom.remoteKeyDao


    @Provides
    @Singleton
    fun providesSearchHistoryDao(
        appRoom: AppDatabase
    ): SearchHistoryDao = appRoom.searchHistoryDao



    @Provides
    @Singleton
    fun providesCollectRemoteKeyDao(
        appRoom: AppDatabase
    ): CollectRemoteKeyDao = appRoom.collectRemoteKeyDao

    @Provides
    @Singleton
    fun providesCollectionDao(
        appRoom: AppDatabase
    ): CollectionDao = appRoom.collectionDao

}
