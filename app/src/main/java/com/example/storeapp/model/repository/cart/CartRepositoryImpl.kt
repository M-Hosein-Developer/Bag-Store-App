package com.example.storeapp.model.repository.cart

import com.example.storeapp.model.apiService.ApiService
import com.google.gson.JsonObject
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val apiService: ApiService, ) : CartRepository {


    override suspend fun addToCart(productId: String): Boolean {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }

        return apiService.addProductToCard(jsonObject).success

    }


}