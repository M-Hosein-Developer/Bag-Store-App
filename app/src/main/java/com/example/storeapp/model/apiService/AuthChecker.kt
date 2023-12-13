package com.example.storeapp.model.apiService

import com.example.storeapp.model.data.LoginResponse
import com.example.storeapp.model.repository.TokenInMemory
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthChecker : Authenticator {

    @Inject
    lateinit var apiService: ApiService

    override fun authenticate(route: Route?, response: Response): Request? {

        if ( TokenInMemory.token != null && !response.request.url.pathSegments.last().equals("refreshToken" , false)){
            val result = refreshToken()
            if (result){

                return response.request

            }
        }
        return null
    }

    private fun refreshToken() : Boolean{

        val request : retrofit2.Response<LoginResponse> = apiService.refreshToken().execute()
        if (request.body() != null){

            if (request.body()!!.success){
                return true
            }

        }
        return false
    }
}