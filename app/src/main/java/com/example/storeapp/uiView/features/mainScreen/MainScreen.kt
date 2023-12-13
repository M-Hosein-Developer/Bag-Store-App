package com.example.storeapp.uiView.features.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.storeapp.uiView.CategoryScreen
import com.example.storeapp.uiView.theme.BackgroundMain
import com.example.storeapp.uiView.theme.MainAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {

        MainAppTheme {

            Surface(modifier = Modifier.fillMaxSize() , color = BackgroundMain) {
                MainScreen()
            }

        }

    }
}

@Composable
fun MainScreen() {

    //Status Bar Color
    val uiController = rememberSystemUiController()
    SideEffect { uiController.setStatusBarColor(Color.White) }

    Column(modifier = Modifier
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
        title = { Text(text = "Bag Store" , fontWeight = FontWeight.Bold)},
        modifier = Modifier.background(Color.White),
        actions = {

            IconButton(onClick = {  }) {
                    Icon(Icons.Default.ShoppingCart , null)
            }

            IconButton(onClick = {  }) {
                Icon(Icons.Default.Person , null)
            }

        }
    )

}

//--CategoryBar-------------------------------------------------------------------------------------

@Composable
fun CategoryBar() {

}

//--ProductSubject----------------------------------------------------------------------------------

@Composable
fun ProductSubject() {

}

//--BigPictureAds-----------------------------------------------------------------------------------

@Composable
fun BigPictureAds() {

}



