package com.example.storeapp.model.repository.commen

import com.example.storeapp.model.data.Comment

interface CommentRepository {

    suspend fun getAllComments(productId : String) : List<Comment>

    suspend fun addNewComment(productId: String , text: String , IsSuccess:(String) -> Unit)
}