package com.muath.composecleanarch.core.di

import android.content.Context
import androidx.room.Room
import com.muath.composecleanarch.core.database.AppDatabase
import com.muath.composecleanarch.core.common.Constants
import com.muath.composecleanarch.core.network.ConnectivityObserver
import com.muath.composecleanarch.core.network.ConnectivityObserverImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

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

    //Connectivity Observer
    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return ConnectivityObserverImp(context)
    }

}