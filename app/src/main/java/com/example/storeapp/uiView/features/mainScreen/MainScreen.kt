package com.example.storeapp.uiView.features.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.storeapp.R
import com.example.storeapp.uiView.theme.BackgroundMain
import com.example.storeapp.uiView.theme.CardViewBackground
import com.example.storeapp.uiView.theme.MainAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {

        MainAppTheme {

            Surface(modifier = Modifier.fillMaxSize(), color = BackgroundMain) {
//                MainScreen()
            }

        }

    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {

    //Status Bar Color
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Color.White) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        TopToolbar()

        CategoryBar()

        ProductSubject()
        ProductSubject()

        BigPictureAds()

        ProductSubject()
        ProductSubject()
    }

}

//--Toolbar-----------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolbar() {

    TopAppBar(
        title = { Text(text = "Bag Store", fontWeight = FontWeight.Bold) },
        modifier = Modifier.background(Color.White),
        actions = {

            IconButton(onClick = { }) {
                Icon(Icons.Default.ShoppingCart, null)
            }

            IconButton(onClick = { }) {
                Icon(Icons.Default.Person, null)
            }

        }
    )

}


//--CategoryBar-------------------------------------------------------------------------------------
@Composable
fun CategoryBar() {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {

        items(10) {
            CategoryItem()
        }

    }

}

@Composable
fun CategoryItem() {

    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(shape = RoundedCornerShape(14.dp), color = CardViewBackground) {

            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_icon_app),
                contentDescription = null
            )

        }

        Text(
            text = "Hotel",
            modifier = Modifier.padding(top = 4.dp),
            style = TextStyle(color = Color.Gray)
        )

    }

}


//--ProductSubject----------------------------------------------------------------------------------
@Composable
fun ProductSubject() {

    Column(modifier = Modifier.padding(top = 32.dp)) {

        Text(
            text = "Popular Destination",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        ProductBar()

    }

}

@Composable
fun ProductBar() {

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp),
    ) {

        items(10) {
            ProductItem()
        }

    }

}

@Composable
fun ProductItem() {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            Image(
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.img_intro)
            )

            Column(Modifier.padding(10.dp)) {

                Text(
                    text = "Diamond Woman Watch",
                    style = TextStyle(fontSize = 15.sp),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "80,000 Tomans",
                    style = TextStyle(fontSize = 14.sp),
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "150 sold",
                    style = TextStyle(fontSize = 13.sp , color = Color.Gray),
                    fontWeight = FontWeight.Medium
                )

            }

        }

    }

}


//--BigPictureAds-----------------------------------------------------------------------------------
@Composable
fun BigPictureAds() {

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(CardDefaults.shape)
            .clickable { },
        painter = painterResource(id = R.drawable.img_intro),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}



