package com.muath.composecleanarch.features.productlist.di

import com.muath.composecleanarch.core.common.Constants
import com.muath.composecleanarch.core.database.AppDatabase
import com.muath.composecleanarch.core.network.ConnectivityObserver
import com.muath.composecleanarch.core.network.ServiceFactory
import com.muath.composecleanarch.features.productlist.data.datasource.ProductLocalDataSource
import com.muath.composecleanarch.features.productlist.data.datasource.ProductLocalDataSourceImp
import com.muath.composecleanarch.features.productlist.data.datasource.ProductRemoteDataSource
import com.muath.composecleanarch.features.productlist.data.datasource.ProductRemoteDataSourceImp
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductDao
import com.muath.composecleanarch.features.productlist.data.remote.api.ProductApiService
import com.muath.composecleanarch.features.productlist.data.repo.ProductRepositoryImp
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductListModule {

    // API
    @Provides @Singleton
    fun provideProductApi(): ProductApiService =
        ServiceFactory.create(Constants.BASE_URL, ProductApiService::class.java)

    // Remote DS
    @Provides @Singleton
    fun provideProductRemoteDataSource(
        api: ProductApiService
    ): ProductRemoteDataSource = ProductRemoteDataSourceImp(api)

    // ProductDao
    @Provides @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    // Local DS
    @Provides @Singleton
    fun provideProductLocalDataSource(
        dao: ProductDao
    ): ProductLocalDataSource = ProductLocalDataSourceImp(dao)

    // Repo
    @Provides @Singleton
    fun provideProductRepository(
        remote: ProductRemoteDataSource,
        local: ProductLocalDataSource,
        connectivity: ConnectivityObserver
    ): ProductRepository = ProductRepositoryImp(remote, local, connectivity)
}
