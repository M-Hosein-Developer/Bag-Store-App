package com.example.storeapp.uiView.features.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.storeapp.R
import com.example.storeapp.uiView.features.product.MainTextField
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.styleTime

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

            ProfileToolbar() { navController.popBackStack() }

            MainAnimation()

            Spacer(modifier = Modifier.padding(top = 6.dp))

            ShowDataSection("Email Address", viewModel.email.value, null)

            ShowDataSection(
                "Address",
                viewModel.address.value
            ) { viewModel.showLocationDialog.value = true }

            ShowDataSection(
                "Postal Code",
                viewModel.postalCode.value
            ) { viewModel.showLocationDialog.value = true }

            ShowDataSection(
                "Login Time",
                styleTime(viewModel.loginTime.value.toLong()),
                null
            )

            Button(
                onClick = {
                    Toast.makeText(context, "Hope to see you again", Toast.LENGTH_SHORT).show()
                    viewModel.signOut()
                    navController.navigate(MyScreens.MainScreen.route){
                        popUpTo(MyScreens.MainScreen.route){
                            inclusive
                        }
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                } ,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 36.dp)
            ) {

                Text(text = "Sign Out")

            }

        }

        //----------------------
        if (viewModel.showLocationDialog.value){

            AddUserLocationDataDialog(
                showSaveLocation = false,
                onDismiss = { viewModel.showLocationDialog.value = false },
                onSubmitClicked ={ address , postalCode , _ ->

                    viewModel.setUserLocation(address , postalCode)

                }
            )


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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, bottom = 16.dp, start = 50.dp, end = 50.dp),
        iterations = LottieConstants.IterateForever
    )

}

@Composable
fun ShowDataSection(subject: String, text: String, onLocationClicked: (() -> Unit)?) {

    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable { onLocationClicked?.invoke() },
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = subject,
            style = TextStyle(fontSize = 18.sp, color = Blue, fontWeight = FontWeight.Bold)
        )

        Text(
            text = text,
            Modifier.padding(top = 2.dp),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )

        Divider(color = Blue, thickness = 0.8.dp, modifier = Modifier.padding(top = 16.dp))

    }
}

@Composable
fun AddUserLocationDataDialog(
    showSaveLocation: Boolean,
    onDismiss: () -> Unit,
    onSubmitClicked: (String, String, Boolean) -> Unit
) {

    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(true) }
    val userAddress = remember { mutableStateOf("") }
    val userPostalCode = remember { mutableStateOf("") }
    val fraction = if (showSaveLocation) 0.340f else 0.270f

    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier.fillMaxHeight(fraction),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = CardDefaults.shape
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Add Location Data",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))

                MainTextField(userAddress.value, "your address...") {
                    userAddress.value = it
                }

                MainTextField(userPostalCode.value, "your postal code...") {
                    userPostalCode.value = it
                }

                if (showSaveLocation) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                        )

                        Text(text = "Save To Profile")

                    }

                }


                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {

                        if (
                            (userAddress.value.isNotEmpty() || userAddress.value.isNotBlank()) &&
                            (userPostalCode.value.isNotEmpty() || userPostalCode.value.isNotBlank())
                        ) {
                            onSubmitClicked(
                                userAddress.value,
                                userPostalCode.value,
                                checkedState.value
                            )
                            onDismiss.invoke()
                        } else {
                            Toast.makeText(context, "please write first...", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}
