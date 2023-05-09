package com.android.dd.wanandroidcompose.data.di

import android.content.Context
import com.android.dd.wanandroidcompose.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesMovieDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = AppDatabase.createDatabase(context)
}
