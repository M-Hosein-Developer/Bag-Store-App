package com.example.storeapp.model.repository.product

import com.example.storeapp.model.data.Ads
import com.example.storeapp.model.data.Product

interface ProductRepository {

    suspend fun getAllProduces(isInternetConnected : Boolean) : List<Product>

    suspend fun getAllAds(isInternetConnected : Boolean) : List<Ads>

    suspend fun getAllProducesByCategory(category : String) : List<Product>

    suspend fun getProductById(productId : String) : Product

}