package com.example.storeapp.model.repository.cart

import com.example.storeapp.model.data.CheckOut
import com.example.storeapp.model.data.SubmitOrder
import com.example.storeapp.model.data.UserCartInfo

interface CartRepository {

    suspend fun addToCart(productId : String) : Boolean
    suspend fun removeFromCart(productId: String) : Boolean
    suspend fun getCartSize() : Int
    suspend fun getUserCartInfo() : UserCartInfo

    suspend fun submitOrder(address: String , postalCode : String) : SubmitOrder
    suspend fun checkout(orderId: String) : CheckOut
    fun setOrderId(orderId: String)
    fun getOrderId(): String
    fun setPurchaseStatus(status : Int)
    fun getPurchaseStatus() : Int

}