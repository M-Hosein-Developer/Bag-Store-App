package com.example.storeapp.uiView.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.storeapp.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val postalCode = mutableStateOf("")
    val loginTime = mutableStateOf("")

    fun loadUserData(){


        email.value = userRepository.getUsername()!!
        loginTime.value = userRepository.getUserLoginTime()

        val location = userRepository.getUserLocation()
        address.value = location.first
        postalCode.value = location.second

    }

}