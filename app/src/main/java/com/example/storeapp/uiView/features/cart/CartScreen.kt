package com.example.storeapp.uiView.features.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.storeapp.R
import com.example.storeapp.model.data.Product
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.uiView.theme.priceBackground
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.stylePrice

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    val getDataDialogState = remember { mutableStateOf(false) }
    viewModel.loadCardData()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)) {

            CartToolbar(
                onBackCLicked = { navController.popBackStack() } ,
                onProfileClicked = { navController.navigate(MyScreens.ProfileScreen.route) }
            )

            if (viewModel.productLit.value.isNotEmpty()){

                CartList(
                    data = viewModel.productLit.value,
                    isChangingNumber = viewModel.isChangingNumber.value,
                    onAddItemClicked = { viewModel.addItem(it) },
                    onRemovedItemClicked = { viewModel.removeItem(it) },
                    onItemClicked = { navController.navigate(MyScreens.ProductScreen.route + "/" +  it) }
                )

            }else{

                NoDataAnimation()

            }


        }

    }
    

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartToolbar(
    onBackCLicked: () -> Unit,
    onProfileClicked: () -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = "My Cart", modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackCLicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onProfileClicked.invoke() } , Modifier.padding(end = 6.dp)) {
               Icon(Icons.Default.Person, null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )

}


@Composable
fun CartList(
    data: List<Product>,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClicked: (String) -> Unit,
    onRemovedItemClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) {

        items(data.size) {
            CartItem(
                data = data[it],
                isChangingNumber = isChangingNumber,
                onAddItemClicked = onAddItemClicked,
                onRemovedItemClicked = onRemovedItemClicked,
                onItemClicked = onItemClicked
            )
        }
    }

}

@Composable
fun CartItem(
    data: Product,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClicked: (String) -> Unit,
    onRemovedItemClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onItemClicked.invoke(data.productId) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            AsyncImage(
                model = data.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    text = data.name,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "From " + data.category + " Group",
                    style = TextStyle(fontSize = 14.sp)
                )

                Text(
                    modifier = Modifier.padding(top = 18.dp),
                    text = "Product authenticity guarantee",
                    style = TextStyle(fontSize = 14.sp)
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "Available in stock ship",
                    style = TextStyle(fontSize = 14.sp)
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Surface(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 6.dp, top = 18.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = priceBackground

                ) {

                    Text(
                        modifier = Modifier.padding(
                            top = 6.dp,
                            bottom = 6.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                        text = stylePrice(
                            (data.price.toInt() * (data.quantity ?: "1").toInt()).toString()
                        ),
                        style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    )

                }

                Surface(
                    modifier = Modifier
                        .padding(bottom = 14.dp, end = 8.dp)
                        .align(Alignment.Bottom)
                ) {

                    Card(
                        border = BorderStroke(2.dp, Blue),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (data.quantity?.toInt() == 1) {

                                IconButton(onClick = { onRemovedItemClicked.invoke(data.productId) }) {
                                    Icon(
                                        modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }

                            } else {

                                IconButton(onClick = { onRemovedItemClicked.invoke(data.productId) }) {
                                    Icon(
                                        modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                        painter = painterResource(R.drawable.ic_minus),
                                        contentDescription = null
                                    )
                                }

                            }

                            //size product
                            if (isChangingNumber.first == data.productId && isChangingNumber.second) {

                                Text(
                                    text = "...",
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )

                            } else {

                                Text(
                                    text = data.quantity ?: "1",
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                            }

                            IconButton(onClick = { onAddItemClicked.invoke(data.productId) }) {
                                Icon(
                                    modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }

                        }

                    }

                }

            }

        }

    }

}

@Composable
fun NoDataAnimation (){

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.no_data)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

}

