package com.example.storeapp.uiView.features.singIn

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.storeapp.R
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.NetworkChecker
import com.example.storeapp.util.VALUE_SUCCESS


@Composable
fun SingInScreen(viewModel: SingInViewModel, navController: NavHostController) {

    val context = LocalContext.current

    clearInputs(viewModel)

    Box {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Blue)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconApp()
            MainCardView(navController, viewModel) {

                viewModel.singInUser{

                    if (it == VALUE_SUCCESS){
                        navController.navigate(MyScreens.MainScreen.route){
                            popUpTo(MyScreens.MainScreen.route){
                                inclusive = true
                            }
                        }
                    }else{
                        Toast.makeText(context, it , Toast.LENGTH_SHORT).show()
                    }

                }

            }

        }

    }

}

@Composable
fun IconApp() {

    Surface(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_icon_app),
            contentDescription = null,
            modifier = Modifier.padding(14.dp)
        )
    }

}

@Composable
fun MainCardView(navigation: NavController, viewModel: SingInViewModel, singInEvent: () -> Unit) {

    //State
    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(Color.White),

        ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sing In",
                Modifier.padding(top = 18.dp, bottom = 18.dp),
                style = TextStyle(
                    color = Blue,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )


            MainTextField(email.value, R.drawable.ic_email, "Email") { viewModel.email.value = it }
            PasswordTextField(
                password.value,
                R.drawable.ic_password,
                "Password"
            ) { viewModel.password.value = it }

            Button(
                onClick = {

                    if (email.value.isNotEmpty() && password.value.isNotEmpty() ) {

                        if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {

                            if (NetworkChecker(context).internetConnection) {

                                singInEvent.invoke()

                            } else {
                                Toast.makeText(context, "Check Your Connection!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Email Format Is Not True", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Please Complete Field...", Toast.LENGTH_SHORT).show()
                    }

                },

                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
            ) {

                Text(text = "Log In", modifier = Modifier.padding(8.dp))

            }

            Row(
                modifier = Modifier.padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don`t have an account?")
                TextButton(onClick = {
                    navigation.navigate(MyScreens.SingUpScreen.route) {
                        popUpTo(MyScreens.SingInScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Register Here", color = Blue)
                }
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {

    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp)
            .padding(12.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(painterResource(icon), null) }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit) {

    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp)
            .padding(12.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(painterResource(icon), null) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value) painterResource(R.drawable.ic_invisible)
            else painterResource(R.drawable.ic_visible)

            Icon(painter = image, contentDescription = null,
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value })
        }
    )

}

fun clearInputs(viewModel: SingInViewModel){
    viewModel.email.value = ""
    viewModel.password.value = ""

}


