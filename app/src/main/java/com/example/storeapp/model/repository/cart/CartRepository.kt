package com.example.storeapp.model.repository.cart

interface CartRepository {

    suspend fun addToCart(productId : String) : Boolean

    suspend fun getCartSize() : Int

}