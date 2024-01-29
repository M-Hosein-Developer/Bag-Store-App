package com.example.storeapp.model.repository.cart

import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.data.UserCartInfo
import com.google.gson.JsonObject
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val apiService: ApiService, ) : CartRepository {


    override suspend fun addToCart(productId: String): Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }

        return apiService.addProductToCard(jsonObject).success

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

    override suspend fun removeFromCart(productId: String) : Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }

        return apiService.removeFromCard(jsonObject).success


    }


}