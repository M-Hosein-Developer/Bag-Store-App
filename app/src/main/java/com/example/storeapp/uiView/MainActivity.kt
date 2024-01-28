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
import com.example.storeapp.uiView.features.IntroScreen
import com.example.storeapp.uiView.features.cart.CartScreen
import com.example.storeapp.uiView.features.cart.CartViewModel
import com.example.storeapp.uiView.features.category.CategoryScreen
import com.example.storeapp.uiView.features.category.CategoryViewModel
import com.example.storeapp.uiView.features.mainScreen.MainScreen
import com.example.storeapp.uiView.features.mainScreen.MainViewModel
import com.example.storeapp.uiView.features.product.ProductScreen
import com.example.storeapp.uiView.features.product.ProductViewModel
import com.example.storeapp.uiView.features.profile.ProfileScreen
import com.example.storeapp.uiView.features.profile.ProfileViewModel
import com.example.storeapp.uiView.features.singIn.SingInScreen
import com.example.storeapp.uiView.features.singIn.SingInViewModel
import com.example.storeapp.uiView.features.singUp.SingUpScreen
import com.example.storeapp.uiView.features.singUp.SingUpViewModel
import com.example.storeapp.uiView.theme.MainAppTheme
import com.example.storeapp.util.KEY_CATEGORY_ARG
import com.example.storeapp.util.KEY_PRODUCT_ARG
import com.example.storeapp.util.MyScreens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val singUpViewModel: SingUpViewModel by viewModels()
    private val singInViewModel: SingInViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val categoryViewModel : CategoryViewModel by viewModels()
    private val productViewModel : ProductViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainAppTheme {
                userRepository.loadToken()
                StoreUi(singUpViewModel, singInViewModel , mainViewModel , categoryViewModel , productViewModel , profileViewModel , cartViewModel)
            }
        }
    }
}

@Composable
fun StoreUi(
    singUpViewModel: SingUpViewModel,
    singInViewModel: SingInViewModel,
    mainViewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
    productViewModel: ProductViewModel,
    profileViewModel: ProfileViewModel,
    cartViewModel: CartViewModel
) {



    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MyScreens.MainScreen.route) {

        composable(MyScreens.MainScreen.route) {

            if (TokenInMemory.token != null) {
                MainScreen(mainViewModel , navController)
            } else {
                IntroScreen(navController)
            }

        }

        composable(
            route = MyScreens.ProductScreen.route + "/" + "{$KEY_PRODUCT_ARG}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })) {
            ProductScreen(it.arguments!!.getString("productId", "null") , productViewModel , navController)
        }

        composable(
            route = MyScreens.CategoryScreen.route + "/" + "{$KEY_CATEGORY_ARG}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })) {

            CategoryScreen(categoryViewModel , navController , it.arguments!!.getString("categoryName", "null"))
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen(profileViewModel , navController)
        }

        composable(MyScreens.CartScreen.route) {
            CartScreen(cartViewModel , navController)
        }

        composable(MyScreens.SingUpScreen.route) {
            SingUpScreen(singUpViewModel, navController)
        }

        composable(MyScreens.SingInScreen.route) {
            SingInScreen(singInViewModel, navController)
        }

    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainAppTheme {


    }
}