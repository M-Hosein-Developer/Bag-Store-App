package com.example.storeapp.uiView.features.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.data.Product
import com.example.storeapp.model.repository.cart.CartRepository
import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val productLit = mutableStateOf(listOf<Product>())
    val totalPrice = mutableStateOf(0)
    val isChangingNumber = mutableStateOf(Pair("", false))

    fun loadCardData() {

        viewModelScope.launch(coroutineExceptionHandler) {
            val data = cartRepository.getUserCartInfo()
            productLit.value = data.productList
            totalPrice.value = data.totalPrice


        }

    }

    fun addItem(productId : String){
        viewModelScope.launch(coroutineExceptionHandler) {

            isChangingNumber.value = isChangingNumber.value.copy(productId , true)
            val isSuccess = cartRepository.addToCart(productId)

            if (isSuccess){
                loadCardData()
            }
            delay(100)
            isChangingNumber.value = isChangingNumber.value.copy(productId , false)
        }
    }

    fun removeItem(productId : String){
        viewModelScope.launch(coroutineExceptionHandler) {

            isChangingNumber.value = isChangingNumber.value.copy(productId , true)
            val isSuccess = cartRepository.removeFromCart(productId)

            if (isSuccess) {
                loadCardData()
            }
            delay(100)
            isChangingNumber.value = isChangingNumber.value.copy(productId, false)

        }
    }

    fun getUserLocation(): Pair<String, String> {
        return userRepository.getUserLocation()
    }

    fun setUserLocation(address: String, postalCode: String) {
        userRepository.saveUserLocation(address, postalCode)
    }

    fun purchaseAll(address: String, postalCode: String, isSuccess: (Boolean , String) -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val result = cartRepository.submitOrder(address, postalCode)
            isSuccess.invoke(result.success , result.paymentLink)
        }
    }

    fun setPaymentStatus(status: Int){
        cartRepository.setPurchaseStatus(status)
    }


}