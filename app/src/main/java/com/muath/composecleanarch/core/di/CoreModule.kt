package com.muath.composecleanarch.core.di

import android.content.Context
import androidx.room.Room
import com.muath.composecleanarch.core.common.DefaultDispatcherProvider
import com.muath.composecleanarch.core.common.DispatcherProvider
import com.muath.composecleanarch.core.database.AppDatabase
import com.muath.composecleanarch.core.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    // Threading
    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        /**
         * Provides the coroutine dispatcher provider.
         * In production → DefaultDispatcherProvider()
         * In tests → you can skip DI and inject a test dispatcher manually.
         */
        return DefaultDispatcherProvider()
    }

    // Room Database
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    //

}