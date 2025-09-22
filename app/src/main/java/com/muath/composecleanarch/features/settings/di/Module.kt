package com.muath.composecleanarch.features.settings.di

import android.content.Context
import com.muath.composecleanarch.features.settings.data.SettingsRepositoryImp
import com.muath.composecleanarch.features.settings.domain.repo.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImp(context)
    }
}