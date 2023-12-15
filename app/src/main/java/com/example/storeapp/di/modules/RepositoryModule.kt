package com.example.storeapp.di.modules

import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.model.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindProductRepository(repository: UserRepositoryImpl) : ProductRepository
}