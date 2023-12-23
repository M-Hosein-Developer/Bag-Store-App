package com.example.storeapp.uiView.features.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProductScreen(
    productId: String,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current


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
                onBackCLicked = {navController.popBackStack()} ,
                onCartClicked = {})

        }

//        AddToCart()

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
                          }}) {
                              Icon(Icons.Default.ShoppingCart, contentDescription = null)
                          }
                      }
                  }
        },
        modifier = Modifier.fillMaxWidth(),
    )

}