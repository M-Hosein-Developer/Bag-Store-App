package com.example.storeapp.uiView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.storeapp.model.repository.TokenInMemory
import com.example.storeapp.model.repository.user.UserRepository
import com.example.storeapp.model.repository.user.UserRepositoryImpl
import com.example.storeapp.uiView.features.IntroScreen
import com.example.storeapp.uiView.features.mainScreen.MainScreen
import com.example.storeapp.uiView.features.singIn.SingInScreen
import com.example.storeapp.uiView.features.singIn.SingInViewModel
import com.example.storeapp.uiView.features.singUp.SingUpScreen
import com.example.storeapp.uiView.features.singUp.SingUpViewModel
import com.example.storeapp.uiView.theme.MainAppTheme
import com.example.storeapp.util.MyScreens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val singUpViewModel: SingUpViewModel by viewModels()
    private val singInViewModel: SingInViewModel by viewModels()

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainAppTheme {
                userRepository.loadToken()
                StoreUi(singUpViewModel, singInViewModel)
            }
        }
    }
}

@Composable
fun StoreUi(singUpViewModel: SingUpViewModel, singInViewModel: SingInViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MyScreens.MainScreen.route) {

        composable(MyScreens.MainScreen.route) {

            if (TokenInMemory.token != null) {
                MainScreen()
            } else {
                IntroScreen(navController)
            }

        }

        composable(
            route = MyScreens.ProductScreen.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })) {
            ProductScreen(it.arguments!!.getInt("productId", -1))
        }

        composable(
            route = MyScreens.CategoryScreen.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })) {
            CategoryScreen(it.arguments!!.getString("categoryName", "null"))
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }

        composable(MyScreens.SingUpScreen.route) {
            SingUpScreen(singUpViewModel, navController)
        }

        composable(MyScreens.SingInScreen.route) {
            SingInScreen(singInViewModel, navController)
        }

        composable(MyScreens.IntroScreen.route) {
            IntroScreen(navController)
        }

        composable(MyScreens.NoInternetScreen.route) {
            NoInternetScreen()
        }

    }

}


@Composable
fun ProductScreen(int: Int) {


}

@Composable
fun CategoryScreen(string: String) {


}

@Composable
fun ProfileScreen() {


}

@Composable
fun CartScreen() {


}


@Composable
fun NoInternetScreen() {


}


@Composable
fun Test() {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainAppTheme {
        Test()
    }
}