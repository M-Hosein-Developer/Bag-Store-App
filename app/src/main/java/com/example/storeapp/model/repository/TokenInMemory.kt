package com.example.storeapp.model.repository

object TokenInMemory {

    var userName : String? = null
        private set

    var token : String? = null
        private set

    fun refreshToken(username : String? , newToken : String?){
        this.userName = username
        this.token = newToken
    }



}