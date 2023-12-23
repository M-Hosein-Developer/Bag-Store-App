package com.example.storeapp.uiView.features.product

import androidx.lifecycle.ViewModel
import com.example.storeapp.model.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {



}