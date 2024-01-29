package com.example.storeapp.model.apiService

import com.example.storeapp.model.data.AddNewCommentResponse
import com.example.storeapp.model.data.AdsResponse
import com.example.storeapp.model.data.CartResponse
import com.example.storeapp.model.data.CommentResponse
import com.example.storeapp.model.data.LoginResponse
import com.example.storeapp.model.data.ProductResponse
import com.example.storeapp.model.data.UserCartInfo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //--Sign in - out-----------------------------------------------------------------------------
    @POST("signUp")
    suspend fun signUp(@Body jsonObject: JsonObject): LoginResponse

    @POST("signIn")
    suspend fun signIn(@Body jsonObject: JsonObject): LoginResponse

    @GET("refreshToken")
    fun refreshToken(): Call<LoginResponse>


    //--Main Screen Api-----------------------------------------------------------------------------
    @GET("getProducts")
    suspend fun getAllProducts(): ProductResponse

    @GET("getSliderPics")
    suspend fun getAllAds(): AdsResponse


    //--Comment-------------------------------------------------------------------------------------
    @POST("getComments")
    suspend fun getAllComments(@Body jsonObject: JsonObject) : CommentResponse

    @POST("addNewComment")
    suspend fun addNewComment(@Body jsonObject: JsonObject) : AddNewCommentResponse

    //--Add To Cart---------------------------------------------------------------------------------
    @POST("addToCart")
    suspend fun addProductToCard(@Body jsonObject: JsonObject) : CartResponse

    //--User Cart-----------------------------------------------------------------------------------
    @GET("getUserCart")
    suspend fun getUserCart() : UserCartInfo

    //--Remove Cart
    @POST("removeFromCart")
    suspend fun removeFromCard(@Body jsonObject: JsonObject) : CartResponse
}