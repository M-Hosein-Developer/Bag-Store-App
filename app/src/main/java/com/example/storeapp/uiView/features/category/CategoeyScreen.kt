package com.example.storeapp.uiView.features.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storeapp.model.data.Product
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.util.MyScreens

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel,
    navController: NavHostController,
    categoryName: String
) {
    viewModel.loadDataByCategory(categoryName)

    val data = viewModel.dataProduct
    Column(modifier = Modifier.fillMaxSize()) {

        CategoryToolbar(categoryName)
        CategoryList(data.value) {
            navController.navigate(MyScreens.ProductScreen.route + "/" + it)
        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryToolbar(categoryName: String) {

   TopAppBar(
       title = { Text(text = categoryName, fontWeight = FontWeight.Bold) },
       modifier = Modifier.background(Color.White).fillMaxWidth(),
   )

}


@Composable
fun CategoryList(data: List<Product>, onProductOnCLicked: (String) -> Unit) {

    LazyColumn(modifier = Modifier.fillMaxSize()){

        items(data.size){
            CategoryItem(data = data[it], onProductOnCLicked = onProductOnCLicked)
        }
    }

}

@Composable
fun CategoryItem(data: Product, onProductOnCLicked: (String) -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onProductOnCLicked.invoke(data.productId) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            AsyncImage(
                model = data.imgUrl,
                contentDescription =null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = data.name ,
                        style = TextStyle(fontSize = 15.sp , fontWeight = FontWeight.Medium)
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = data.price + " Tomans" ,
                        style = TextStyle(fontSize = 14.sp)
                    )

                }

                Surface(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .align(Alignment.Bottom)
                        .clip(RoundedCornerShape(10.dp)),
                    color = Blue
                ) {

                    Text(
                        text = data.soldItem + " Sold",
                        modifier = Modifier.padding(4.dp),
                        style = TextStyle(fontSize = 13.sp , color = Color.White , fontWeight = FontWeight.Medium)
                    )

                }

            }

        }

    }


}
