package com.example.storeapp.model.repository.user

import android.content.SharedPreferences
import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.repository.TokenInMemory
import com.example.storeapp.util.VALUE_SUCCESS
import com.google.gson.JsonObject
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService : ApiService , private val sharedPrf : SharedPreferences) : UserRepository {

    override suspend fun signUp(name: String, username: String, password: String) : String{

        val jsonObject = JsonObject().apply {
            addProperty("name" , name)
            addProperty("email" , username)
            addProperty("password" , password)
        }


        val result = apiService.signUp(jsonObject)
        if (result.success){

          TokenInMemory.refreshToken(username , result.token)
          saveToken(result.token)
          saveUsername(username)

            return VALUE_SUCCESS
        }else{
            return result.message
        }

    }

    override suspend fun signIn(username: String, password: String) : String {

        val jsonObject = JsonObject().apply {
            addProperty("email", username)
            addProperty("password", password)
        }

        val result = apiService.signIn(jsonObject)
        if (result.success){

            TokenInMemory.refreshToken(username , result.token)
            saveToken(result.token)
            saveUsername(username)

            return VALUE_SUCCESS

        }else{
            return result.message
        }

    }


    override fun singOut() {
        TokenInMemory.refreshToken(null , null)
        sharedPrf.edit().clear().apply()
    }

    override fun loadToken() {
        TokenInMemory.refreshToken(getUsername() , getToken())
    }

    override fun saveToken(newToken: String) {
        sharedPrf.edit().putString("token" , newToken).apply()
    }

    override fun getToken(): String? {
        return sharedPrf.getString("token" , null)
    }

    override fun saveUsername(username: String) {
        sharedPrf.edit().putString("username" , username).apply()

    }

    override fun getUsername(): String? {
        return sharedPrf.getString("username" , null)
    }


}