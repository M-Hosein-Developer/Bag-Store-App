package com.example.storeapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ProductResponse(
    val success : Boolean,
    val products : List<Product>
)

@Entity("product_table")
data class Product(
    val category: String,
    val detailText: String,
    val imgUrl: String,
    val material: String,
    val name: String,
    val price: String,

    @PrimaryKey
    val productId: String,

    val soldItem: String,
    val tags: String
)