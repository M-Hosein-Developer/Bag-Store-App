package com.example.storeapp.uiView.features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.storeapp.R
import com.example.storeapp.uiView.theme.MainAppTheme

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {

    val context = LocalContext.current
    viewModel.loadUserData()

    Box {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileToolbar() {navController.popBackStack()}

            MainAnimation()

            ShowDataSection()

        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileToolbar(onBackedClicked: () -> Unit) {

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = "Profile",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 58.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackedClicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
    )

}

@Composable
fun MainAnimation() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.profile_anim)
    )

    LottieAnimation(
        composition = composition,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 36.dp , bottom = 16.dp , start = 50.dp , end = 50.dp),
        iterations = LottieConstants.IterateForever
        )

}

@Composable
fun ShowDataSection() {


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainAppTheme {
        ProfileToolbar {

        }
    }
}

