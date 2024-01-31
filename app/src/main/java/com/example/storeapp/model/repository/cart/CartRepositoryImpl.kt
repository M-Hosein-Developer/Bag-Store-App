package com.example.storeapp.model.repository.cart

import android.content.SharedPreferences
import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.data.CheckOut
import com.example.storeapp.model.data.SubmitOrder
import com.example.storeapp.model.data.UserCartInfo
import com.example.storeapp.util.NO_PAYMENT
import com.google.gson.JsonObject
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val apiService: ApiService, private val sharesPref : SharedPreferences) : CartRepository {


    override suspend fun addToCart(productId: String): Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }

        return apiService.addProductToCard(jsonObject).success

    }

    override suspend fun removeFromCart(productId: String) : Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }

        return apiService.removeFromCard(jsonObject).success


    }


    override suspend fun getCartSize(): Int {

        val result = apiService.getUserCart()

         if (result.success){
            var counter = 0
            result.productList.forEach {

                counter += ( it.quantity ?: "0" ).toInt()

            }

             return counter

        }else{
             return 0
        }

    }



    override suspend fun getUserCartInfo(): UserCartInfo {
        return apiService.getUserCart()
    }

    //Payment
    override suspend fun submitOrder(address: String, postalCode: String): SubmitOrder {

        val jsonObject = JsonObject().apply {
            addProperty("address" , address)
            addProperty("postalCode" , postalCode)
        }

        val result = apiService.submitOrder(jsonObject)
        setOrderId(result.orderId.toString())

        return result
    }

    override suspend fun checkout(orderId: String): CheckOut {

        val jsonObject = JsonObject().apply {
            addProperty("address" , orderId)
        }

        return apiService.checkout(jsonObject)
    }

    override fun setOrderId(orderId: String) {
        sharesPref.edit().putString("orderId" , orderId).apply()
    }

    override fun getOrderId(): String {
        return sharesPref.getString("orderId" , "0")!!
    }

    override fun setPurchaseStatus(status: Int) {
        sharesPref.edit().putInt("purchase_status" , status).apply()
    }

    override fun getPurchaseStatus(): Int {
        return sharesPref.getInt("purchase_status" , NO_PAYMENT)
    }


}