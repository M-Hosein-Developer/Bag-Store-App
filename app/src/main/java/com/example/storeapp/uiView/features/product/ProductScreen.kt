package com.example.storeapp.uiView.features.product

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storeapp.model.data.Product
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.NetworkChecker


@Composable
fun ProductScreen(
    productId: String,
    viewModel: ProductViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    viewModel.loadData(productId)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
        ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 58.dp)
        ) {

            ProductToolbar(
                productName = "Detail",
                badgeNumber = 7,
                onBackCLicked = { navController.popBackStack() },
                onCartClicked = {
                    if (NetworkChecker(context).internetConnection) navController.navigate(MyScreens.CartScreen.route)
                    else Toast.makeText(
                        context,
                        "please connect to internet first...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            val data = viewModel.thisProduct.value
            ProductItem(data = data , onCategoryClicked = {
                navController.navigate(MyScreens.CategoryScreen.route + "/" + it)
            })


        }

        AddToCart()

    }

}

@Composable
fun ProductItem(data: Product , onCategoryClicked:(String) -> Unit) {

    Column(Modifier.padding(16.dp)) {

        ProductDesign(data, onCategoryClicked)

    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductToolbar(
    productName : String ,
    badgeNumber : Int ,
    onBackCLicked:() -> Unit ,
    onCartClicked:() -> Unit
){

    TopAppBar(
        title = {
                Text(text = productName, modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                    textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
                  IconButton(onClick = { onCartClicked.invoke() } , Modifier.padding(end = 6.dp)) {
                      if (badgeNumber == 0){
                          Icon(Icons.Default.ShoppingCart, contentDescription = null)
                      }else{
                          BadgedBox(badge = { Badge {
                              Text(badgeNumber.toString())
                          }
                          }) {
                              Icon(Icons.Default.ShoppingCart, contentDescription = null)
                          }
                      }
                  }
        },
        modifier = Modifier.fillMaxWidth(),
    )

}

@Composable
fun ProductDesign(data: Product, onCategoryClicked: (String) -> Unit) {

    AsyncImage(
        model = data.imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(CardDefaults.shape)
    )

    Text(
        text = data.name,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 14.dp)
    )

    Text(
        text = data.detailText,
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Justify),
        modifier = Modifier.padding(top = 4.dp)
    )

    TextButton(onClick = { onCategoryClicked.invoke(data.category) }) {
        Text(text = "#" + data.category, style = TextStyle(fontSize = 13.sp))
    }

}

@Composable
fun AddToCart() {


}