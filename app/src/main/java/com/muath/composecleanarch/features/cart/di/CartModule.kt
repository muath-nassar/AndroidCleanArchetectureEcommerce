package com.muath.composecleanarch.features.cart.di

import com.muath.composecleanarch.core.database.AppDatabase
import com.muath.composecleanarch.features.cart.data.datasource.CartLocalDataSource
import com.muath.composecleanarch.features.cart.data.datasource.CartLocalDataSourceImpl
import com.muath.composecleanarch.features.cart.data.local.dao.CartDao
import com.muath.composecleanarch.features.cart.data.repo.CartRepositoryImp
import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    //dao
    @Provides
    @Singleton
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    //datasource
    @Provides
    @Singleton
    fun provideCartLocalDataSource(cartDao: CartDao): CartLocalDataSource {
        return CartLocalDataSourceImpl(cartDao)
    }

    // repo
    @Provides
    @Singleton
    fun provideCartRepository(cartLocalDataSource: CartLocalDataSource): CartRepository {
        return CartRepositoryImp(cartLocalDataSource)
    }
}