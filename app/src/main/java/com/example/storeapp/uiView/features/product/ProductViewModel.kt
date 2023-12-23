package com.example.storeapp.uiView.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.util.EMPTY_PRODUCT
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)

    private fun loadProductFromCache(productId : String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            thisProduct.value = repository.getProductById(productId)
        }

    }

    fun loadData(productId : String){

        loadProductFromCache(productId)

    }

}