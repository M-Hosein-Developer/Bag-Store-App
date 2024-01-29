package com.example.storeapp.model.repository.cart

import com.example.storeapp.model.data.UserCartInfo

interface CartRepository {

    suspend fun addToCart(productId : String) : Boolean
    suspend fun removeFromCart(productId: String) : Boolean
    suspend fun getCartSize() : Int
    suspend fun getUserCartInfo() : UserCartInfo

}