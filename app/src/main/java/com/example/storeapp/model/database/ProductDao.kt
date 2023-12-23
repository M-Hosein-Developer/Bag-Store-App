package com.example.storeapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storeapp.model.data.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(product: List<Product>)

    @Query("SELECT * FROM product_table")
    suspend fun getAll() : List<Product>

    @Query("SELECT * FROM product_table WHERE productId = :id ")
    suspend fun getProductById(id : String) : Product

    @Query("SELECT * FROM product_table WHERE category = :category")
    suspend fun getAllByCategory(category : String) : List<Product>

}