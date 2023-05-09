package com.android.dd.wanandroidcompose.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.android.dd.wanandroidcompose.constant.Constant
import com.android.dd.wanandroidcompose.data.entity.*
import com.android.dd.wanandroidcompose.data.serializer.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesDataStoreAuthorTab(
        @ApplicationContext context: Context,
        userPreferencesSerializer: AuthorTabSerializer,
    ): DataStore<AuthorTab> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile(Constant.AuthorTab)
        }

    @Provides
    @Singleton
    fun providesDataStoreHotKeyList(
        @ApplicationContext context: Context,
        userPreferencesSerializer: HotKeyListSerializer,
    ): DataStore<HotKeyList> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile(Constant.HotKey)
        }

    @Provides
    @Singleton
    fun providesDataStoreNavigatorTab(
        @ApplicationContext context: Context,
        userPreferencesSerializer: NavigatorTabSerializer,
    ): DataStore<NavigatorTab> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile(Constant.NavigatorTab)
        }

    @Provides
    @Singleton
    fun providesDataStoreProjectTab(
        @ApplicationContext context: Context,
        userPreferencesSerializer: ProjectTabSerializer,
    ): DataStore<ProjectTab> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile(Constant.ProjectTab)
        }

    @Provides
    @Singleton
    fun providesDataStoreSeriesTab(
        @ApplicationContext context: Context,
        userPreferencesSerializer: SeriesTabSerializer,
    ): DataStore<SeriesTab> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        ) {
            context.dataStoreFile(Constant.SeriesTab)
        }

}
