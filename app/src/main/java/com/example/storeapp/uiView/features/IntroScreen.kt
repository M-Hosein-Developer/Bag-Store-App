package com.example.storeapp.uiView.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.storeapp.R
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.util.MyScreens


@Composable
fun IntroScreen(navController: NavController) {


    Image(
        painter = painterResource(R.drawable.img_intro),
        contentDescription = null,
        Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.78f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { navController.navigate(MyScreens.SingUpScreen.route) }) { Text(text = "Sing Up") }


        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { navController.navigate(MyScreens.SingInScreen.route) }) { Text(text = "Sing In", color = Blue) }

    }

}