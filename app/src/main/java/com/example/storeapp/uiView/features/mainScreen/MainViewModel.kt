package com.example.storeapp.uiView.features.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.data.Ads
import com.example.storeapp.model.data.Product
import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ProductRepository , isInternetConnected : Boolean) : ViewModel(){

    val productData = mutableStateOf<List<Product>>(listOf())
    val adsData = mutableStateOf<List<Ads>>(listOf())
    val showProgressBar = mutableStateOf(false)

    init {
        refreshAllDataFromNet(isInternetConnected)
    }

    private fun refreshAllDataFromNet(isInternetConnected : Boolean){

        viewModelScope.launch(coroutineExceptionHandler){

            if (isInternetConnected)
                showProgressBar.value = true

            delay(1200)
            val newDataProducts = async { repository.getAllProduces(isInternetConnected) }
            val newDataAds = async { repository.getAllAds(isInternetConnected) }

            updateData(newDataProducts.await() , newDataAds.await())

            showProgressBar.value = false

        }

    }

    private fun updateData(newDataProducts: List<Product>, newDataAds: List<Ads>) {

        productData.value = newDataProducts
        adsData.value = newDataAds

    }
}