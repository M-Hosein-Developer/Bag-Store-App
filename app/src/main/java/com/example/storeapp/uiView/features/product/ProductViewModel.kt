package com.example.storeapp.uiView.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.data.Comment
import com.example.storeapp.model.repository.commen.CommentRepository
import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.util.EMPTY_PRODUCT
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository , private val commentRepository: CommentRepository) : ViewModel() {

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)
    val comments = mutableStateOf(listOf<Comment>())

    private fun loadProductFromCache(productId : String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            thisProduct.value = productRepository.getProductById(productId)
        }

    }

    fun loadData(productId : String , isInternetConnected : Boolean){

        loadProductFromCache(productId)

        if (isInternetConnected) loadAllComments(productId)

    }

    private fun loadAllComments(productId : String){

        viewModelScope.launch(coroutineExceptionHandler){

            comments.value = commentRepository.getAllComments(productId)
        }

    }

    fun addNewComment(productId: String, text: String, IsSuccess: (String) -> Unit){

        viewModelScope.launch(coroutineExceptionHandler) {

            commentRepository.addNewComment(productId , text , IsSuccess)

            delay(100)

            comments.value = commentRepository.getAllComments(productId)
        }

    }

}