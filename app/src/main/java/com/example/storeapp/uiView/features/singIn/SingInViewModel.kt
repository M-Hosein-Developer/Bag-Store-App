package com.example.storeapp.uiView.features.singIn


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.model.repository.user.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData ( "" )
    val password = MutableLiveData ("" )


    fun singInUser(loggingEvent : (String) -> Unit){

        viewModelScope.launch {

            val result = userRepository.signIn(email.value!!, password.value!!)
            loggingEvent(result)

        }

    }

}