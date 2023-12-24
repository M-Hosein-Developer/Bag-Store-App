package com.example.storeapp.model.repository.commen

import com.example.storeapp.model.apiService.ApiService
import com.example.storeapp.model.data.Comment
import com.google.gson.JsonObject
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val apiService: ApiService, ) : CommentRepository {


    override suspend fun getAllComments(productId: String): List<Comment> {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
        }
        val data = apiService.getAllComments(jsonObject)

        if (data.success) return data.comments else return listOf()

    }

    override suspend fun addNewComment(productId: String, text: String, IsSuccess: (String) -> Unit) {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
            addProperty("text" , text)
        }
        val result = apiService.addNewComment(jsonObject)

        if (result.success){
            IsSuccess.invoke(result.message)
        }else{IsSuccess.invoke("Comment not added")}

    }


}