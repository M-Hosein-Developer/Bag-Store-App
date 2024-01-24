package com.example.storeapp.uiView.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.data.Comment
import com.example.storeapp.model.repository.cart.CartRepository
import com.example.storeapp.model.repository.commen.CommentRepository
import com.example.storeapp.model.repository.product.ProductRepository
import com.example.storeapp.util.EMPTY_PRODUCT
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository ,
    private val commentRepository: CommentRepository,
    private val cardRepository: CartRepository
) : ViewModel() {

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)
    val comments = mutableStateOf(listOf<Comment>())
    val isAddingProduct = mutableStateOf(false)

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

    fun addProductToCart(productId: String , addingToCartResult:(String) -> Unit){

        viewModelScope.launch(coroutineExceptionHandler) {

            isAddingProduct.value = true
            val result = cardRepository.addToCart(productId)
            delay(500)
            isAddingProduct.value = false

            if (result) addingToCartResult.invoke("Product Added To Cart")
            else addingToCartResult.invoke("Product Not Added To Cart")


        }

    }

}