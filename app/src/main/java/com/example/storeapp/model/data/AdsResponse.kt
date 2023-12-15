package com.example.storeapp.model.data

data class AdsResponse(
    val ads: List<Ads>,
    val success: Boolean
)

data class Ads(
    val adId: String,
    val imageURL: String,
    val productId: String
)
