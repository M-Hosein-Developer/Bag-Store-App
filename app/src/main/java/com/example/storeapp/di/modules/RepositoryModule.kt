package com.example.storeapp.di.modules

import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.model.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PostRepositoryModule {

    @Binds
    abstract fun bindPostRepository(repository: UserRepositoryImpl): UserRepository
}