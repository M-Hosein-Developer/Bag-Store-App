package com.example.storeapp.uiView.features.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.data.Product
import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    val dataProduct = mutableStateOf<List<Product>>(listOf())

    fun loadDataByCategory(category : String){

        viewModelScope.launch(coroutineExceptionHandler) {
            val dataFromDb = productRepository.getAllProducesByCategory(category)
            dataProduct.value = dataFromDb
        }

    }
}