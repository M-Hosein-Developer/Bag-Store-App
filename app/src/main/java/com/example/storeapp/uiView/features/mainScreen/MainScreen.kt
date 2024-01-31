package com.example.storeapp.uiView.features.mainScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storeapp.R
import com.example.storeapp.model.data.Ads
import com.example.storeapp.model.data.CheckOut
import com.example.storeapp.model.data.Product
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.uiView.theme.CardViewBackground
import com.example.storeapp.util.CATEGORY
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.NO_PAYMENT
import com.example.storeapp.util.NetworkChecker
import com.example.storeapp.util.PAYMENT_PENDING
import com.example.storeapp.util.PAYMENT_SUCCESS
import com.example.storeapp.util.TAGS
import com.example.storeapp.util.stylePrice
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {

    //Status Bar Color
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Color.White) }

    //data from view model
    val productDataState = viewModel.productData
    val adsDataState = viewModel.adsData

    val context = LocalContext.current

    if (NetworkChecker(context).internetConnection){
        viewModel.loadBadgeNumber()
    }

    if (viewModel.getPaymentStatus() == PAYMENT_PENDING){

        if (NetworkChecker(context).internetConnection){
            viewModel.getCheckOutData()
        }

    }

    Box {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {

            if (viewModel.showProgressBar.value)
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Blue)

            TopToolbar(
                badgeNumber = viewModel.badgeNumber.value,
                onCartClicked = { if (NetworkChecker(context).internetConnection) navController.navigate(MyScreens.CartScreen.route) else
                    Toast.makeText(context, "please connect to Internet", Toast.LENGTH_SHORT).show()} ,
                onProfileClicked = {navController.navigate(MyScreens.ProfileScreen.route)}
            )
            CategoryBar(CATEGORY){
                navController.navigate(MyScreens.CategoryScreen.route + "/" + it)
            }
            ProductSubjectList(TAGS, productDataState.value, adsDataState.value){
                navController.navigate(MyScreens.ProductScreen.route + "/" + it)
            }

        }


        if (viewModel.showPaymentResultDialog.value){
            PaymentResultDialog(
                checkoutResult = viewModel.checkOutData.value,
                onDismiss = {
                    viewModel.showPaymentResultDialog.value
                    viewModel.setPaymentStatus(NO_PAYMENT)
                }
            )
        }

    }

}

//--Toolbar-----------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolbar(badgeNumber: Int , onCartClicked : () -> Unit , onProfileClicked : () -> Unit) {

    TopAppBar(
        title = { Text(text = "Bag Store", fontWeight = FontWeight.Bold) },
        modifier = Modifier.background(Color.White),
        actions = {
            IconButton(onClick = { onCartClicked.invoke() } , Modifier.fillMaxSize(0.16f)) {
                if (badgeNumber == 0){
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                }else{
                    BadgedBox(modifier = Modifier.padding(top = 8.dp), badge = { Badge {
                        Text(badgeNumber.toString())
                    }
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }

            IconButton(onClick = { onProfileClicked.invoke() } ,modifier = Modifier.padding(top = 8.dp)) {
                Icon(Icons.Default.Person, null)
            }
        }



    )

}


//--CategoryBar-------------------------------------------------------------------------------------
@Composable
fun CategoryBar(categoryList: List<Pair<String, Int>> , onCategoryClicked : (String) -> Unit) {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(categoryList.size) {
            CategoryItem(categoryList[it] , onCategoryClicked)
        }

    }

}

@Composable
fun CategoryItem(subject: Pair<String, Int> , onCategoryClicked : (String) -> Unit) {

    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onCategoryClicked.invoke(subject.first) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(shape = RoundedCornerShape(14.dp), color = CardViewBackground) {

            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = subject.second),
                contentDescription = null
            )

        }

        Text(
            text = subject.first,
            modifier = Modifier.padding(top = 4.dp),
            style = TextStyle(color = Color.Gray)
        )

    }

}


//--ProductSubject----------------------------------------------------------------------------------
@Composable
fun ProductSubjectList(tags: List<String>, product: List<Product>, ads: List<Ads> , onProductClicked : (String) -> Unit) {

    if (product.isNotEmpty()){

        Column {

            tags.forEachIndexed { it, _ ->

                val withTagData = product.filter { product -> product.tags == tags[it] }
                ProductSubject(tags[it], withTagData.shuffled() , onProductClicked)

                if (ads.size >= 2)
                    if (it == 1 || it == 2)
                        BigPictureAds(ads[it-1] , onProductClicked)

            }
        }
    }
}


@Composable
fun ProductSubject(subject: String, data: List<Product> , onProductClicked : (String) -> Unit) {

    Column(modifier = Modifier.padding(top = 32.dp)) {

        Text(
            text = subject,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        ProductBar(data , onProductClicked)

    }

}

@Composable
fun ProductBar(data: List<Product> , onProductClicked : (String) -> Unit) {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp),
    ) {

        items(data.size) {
            ProductItem(data[it] , onProductClicked)
        }

    }

}

@Composable
fun ProductItem(data: Product , onProductClicked : (String) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductClicked.invoke(data.productId) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            AsyncImage(
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop,
                model = data.imgUrl
            )

            Column(Modifier.padding(10.dp)) {

                Text(
                    text = data.name,
                    style = TextStyle(fontSize = 15.sp),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stylePrice(data.price),
                    style = TextStyle(fontSize = 14.sp),
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = data.soldItem + " Sold",
                    style = TextStyle(fontSize = 13.sp, color = Color.Gray),
                    fontWeight = FontWeight.Medium
                )

            }

        }

    }

}


//--BigPictureAds-----------------------------------------------------------------------------------
@Composable
fun BigPictureAds(ads: Ads , onProductClicked : (String) -> Unit) {

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(CardDefaults.shape)
            .clickable { onProductClicked.invoke(ads.productId) },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        model = ads.imageURL
    )

}

@Composable
private fun PaymentResultDialog(checkoutResult: CheckOut, onDismiss: () -> Unit) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            shape = CardDefaults.shape
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Payment Result",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Main Data
                if (checkoutResult.order?.status?.toInt() == PAYMENT_SUCCESS) {

                    AsyncImage(
                        model = R.drawable.success_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(110.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was successful!", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Purchase Amount: " + stylePrice(
                            (checkoutResult.order!!.amount).substring(
                                0,
                                (checkoutResult.order!!.amount).length - 1
                            )
                        )
                    )

                } else {

                    AsyncImage(
                        model = R.drawable.fail_anim,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(110.dp)
                            .padding(top = 6.dp, bottom = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Payment was not successful!", style = TextStyle(fontSize = 16.sp))
                    Text(
                        text = "Purchase Amount: " + stylePrice(
                            (checkoutResult.order!!.amount).substring(
                                0,
                                (checkoutResult.order.amount).length - 1
                            )
                        )
                    )

                }

                // Ok Button
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "ok")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        }
    }
}



