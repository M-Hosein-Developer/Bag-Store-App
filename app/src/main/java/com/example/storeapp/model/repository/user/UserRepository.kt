package com.example.storeapp.model.repository.user

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


interface UserRepository {

    //Online
    suspend fun signUp(name : String , username : String , password : String) : String
    suspend fun signIn(username: String , password: String) : String

    //Offline
    fun singOut()
    fun loadToken()

    fun saveToken(newToken : String)
    fun getToken() : String?

    fun saveUsername(username: String)
    fun getUsername() : String?


}