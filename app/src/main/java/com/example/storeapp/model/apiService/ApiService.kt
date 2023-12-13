package com.example.storeapp.model.apiService

import com.example.storeapp.model.data.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("signUp")
    suspend fun signUp(@Body jsonObject: JsonObject) : LoginResponse

    @POST("signIn")
    suspend fun signIn(@Body jsonObject: JsonObject) : LoginResponse

    @GET("refreshToken")
    fun refreshToken() : Call<LoginResponse>

}