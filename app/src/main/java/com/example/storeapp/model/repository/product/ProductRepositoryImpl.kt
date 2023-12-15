package com.example.storeapp.model.repository.product

import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.data.Ads
import com.example.storeapp.model.data.Product
import com.example.storeapp.model.database.ProductDao
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {


    override suspend fun getAllProduces(isInternetConnected: Boolean): List<Product> {

        return if (isInternetConnected) {

            //get data from api
            val dataFromServer = apiService.getAllProducts()

            if (dataFromServer.success)
                productDao.insertOrUpdate(dataFromServer.products)

            dataFromServer.products

        } else {
            //get data from db
            productDao.getAll()
        }

        return listOf()
    }

    override suspend fun getAllAds(isInternetConnected: Boolean): List<Ads> {

        if (isInternetConnected) {

            val dataFromServer = apiService.getAllAds()

            if (dataFromServer.success)
               return dataFromServer.ads

        }
        return listOf()
    }
}