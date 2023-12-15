package com.example.storeapp.di.modules

import android.content.Context
import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.repository.TokenInMemory
import com.example.storeapp.util.BASE_URL
import com.example.storeapp.util.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun okHttpClient() : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor{

            val oldRequest = it.request()

            val newRequest = oldRequest.newBuilder()

            if (TokenInMemory.token != null)
                newRequest.addHeader("Authorization" , TokenInMemory.token!!)

            newRequest.addHeader("Accept" , "application/json")
            newRequest.method(oldRequest.method , oldRequest.body)

            return@addInterceptor it.proceed(newRequest.build())

        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): Boolean {
        return NetworkChecker(context).internetConnection
    }

}