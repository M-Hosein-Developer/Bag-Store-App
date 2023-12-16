package com.example.storeapp.uiView.features.singUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val name = MutableLiveData ( "" )
    val email = MutableLiveData ( "" )
    val password = MutableLiveData ("" )
    val confirmPassword = MutableLiveData ( "" )

    fun singUpUser(loggingEvent : (String) -> Unit){

        viewModelScope.launch(coroutineExceptionHandler) {

            val result = userRepository.signUp(name.value!!, email.value!!, password.value!!)
            loggingEvent(result)

        }
    }

}
