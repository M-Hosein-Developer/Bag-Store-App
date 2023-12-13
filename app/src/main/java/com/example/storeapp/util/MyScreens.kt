package com.example.storeapp.util

sealed class MyScreens(val route : String){

    object MainScreen : MyScreens("mainScreen")
    object ProductScreen : MyScreens("productId")
    object CategoryScreen : MyScreens("categoryName")
    object ProfileScreen : MyScreens("profileScreen")
    object CartScreen : MyScreens("cartScreen")
    object SingUpScreen : MyScreens("singUpScreen")
    object SingInScreen : MyScreens("singInScreen")
    object IntroScreen : MyScreens("introScreen")
    object NoInternetScreen : MyScreens("noInternetScreen")


}
