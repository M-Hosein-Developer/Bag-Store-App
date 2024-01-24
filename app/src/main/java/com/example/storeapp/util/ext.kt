package com.example.storeapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error" , "Error -> " + throwable.message)
}

fun stylePrice(oldPrice : String) : String{

    if (oldPrice.length > 3){

        val reversed = oldPrice.reversed()
        var newPrice = ""

        for (i in oldPrice.indices){
            newPrice += reversed[i]

            if ((i+1) % 3 == 0){
                newPrice += ","
            }
        }

        val readyToGo = newPrice.reversed()

        if (readyToGo.first() == ','){
            return readyToGo.substring(1) + " Tomans"
        }

        return readyToGo + " Tomans"

    }

    return oldPrice + " Tomans"
}