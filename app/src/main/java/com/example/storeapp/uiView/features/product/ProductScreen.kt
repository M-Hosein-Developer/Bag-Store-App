package com.example.storeapp.uiView.features.product

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.storeapp.R
import com.example.storeapp.model.data.Comment
import com.example.storeapp.model.data.Product
import com.example.storeapp.uiView.theme.Blue
import com.example.storeapp.uiView.theme.MainAppTheme
import com.example.storeapp.util.MyScreens
import com.example.storeapp.util.NetworkChecker


@Composable
fun ProductScreen(
    productId: String,
    viewModel: ProductViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    viewModel.loadData(productId, NetworkChecker(context).internetConnection)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                onBackCLicked = { navController.popBackStack() },
                onCartClicked = {
                    if (NetworkChecker(context).internetConnection) navController.navigate(MyScreens.CartScreen.route)
                    else Toast.makeText(
                        context,
                        "please connect to internet first...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )

            val data = viewModel.thisProduct.value
            val comment = viewModel.comments.value
            ProductItem(
                data = data, comment ,
                onCategoryClicked = { navController.navigate(MyScreens.CategoryScreen.route + "/" + it)},
                onAddNewComment = { it -> viewModel.addNewComment(productId , it , { Toast.makeText(context, it, Toast.LENGTH_SHORT).show()}) }
            )


        }

        AddToCart()

    }

}

@Composable
fun ProductItem(
    data: Product, comment: List<Comment>,
    onCategoryClicked: (String) -> Unit,
    onAddNewComment:(String) -> Unit) {

    Column(Modifier.padding(16.dp)) {

        ProductDesign(data, onCategoryClicked)

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
        )

        ProductDetail(data, comment.size.toString())

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 14.dp, bottom = 4.dp)
        )

        ProductComments(comment, onAddNewComment)

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
                          }
                          }) {
                              Icon(Icons.Default.ShoppingCart, contentDescription = null)
                          }
                      }
                  }
        },
        modifier = Modifier.fillMaxWidth(),
    )

}

@Composable
fun ProductDesign(data: Product, onCategoryClicked: (String) -> Unit) {

    AsyncImage(
        model = data.imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(CardDefaults.shape)
    )

    Text(
        text = data.name,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
        modifier = Modifier.padding(top = 14.dp)
    )

    Text(
        text = data.detailText,
        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Justify),
        modifier = Modifier.padding(top = 4.dp)
    )

    TextButton(onClick = { onCategoryClicked.invoke(data.category) }) {
        Text(text = "#" + data.category, style = TextStyle(fontSize = 13.sp))
    }

}

@Composable
fun ProductDetail(data: Product, commentNumber: String) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        Column {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_comment),
                    contentDescription = null,
                    Modifier.size(26.dp)
                )
                Text(
                    text = commentNumber + " Comments",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_material),
                    contentDescription = null,
                    Modifier.size(26.dp)
                )
                Text(
                    text = data.material,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

            Row(verticalAlignment = Alignment.CenterVertically , modifier = Modifier.padding(top = 10.dp)) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_sold),
                    contentDescription = null,
                    Modifier.size(26.dp)
                )
                Text(
                    text = data.soldItem,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }

        }

        Surface(
            modifier = Modifier
                .clip(CardDefaults.shape)
                .align(Alignment.Bottom),
            color = Blue
        ) {

            Text(
                text = data.tags,
                color = Color.White,
                modifier = Modifier.padding(6.dp),
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
            )

        }


    }

}

@Composable
fun ProductComments(comment: List<Comment>, addNewComment: (String) -> Unit) {

    val showCommentDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (comment.isNotEmpty()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Comments",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
            )

            TextButton(onClick = {

                if (NetworkChecker(context).internetConnection) {
                    showCommentDialog.value = true
                } else {
                    Toast.makeText(
                        context,
                        "connect to internet to add comment...",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }) {
                Text(text = "Add New Comment", style = TextStyle(fontSize = 14.sp))
            }
        }

        comment.forEach {
            CommentBody(comment = it)
        }

    } else {

        TextButton(onClick = {

            if (NetworkChecker(context).internetConnection) {
                showCommentDialog.value = true
            } else {
                Toast.makeText(context, "connect to internet to add comment...", Toast.LENGTH_SHORT)
                    .show()
            }

        }) {
            Text(text = "Add New Comment", style = TextStyle(fontSize = 14.sp))
        }

    }

    if (showCommentDialog.value) {
        AddNewComment(
            onDismiss = { showCommentDialog.value = false },
            onPositiveClicked = {
                addNewComment.invoke(it)
            }
        )
    }

}

@Composable
fun AddNewComment(onDismiss: () -> Unit, onPositiveClicked: (String) -> Unit) {

    val context = LocalContext.current
    val userComment = remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            modifier = Modifier.fillMaxHeight(0.20f),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = CardDefaults.shape
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Write Your Comment",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                //enter data
                MainTextField(
                    edtValue = userComment.value,
                    hint = "write something..."
                ) {
                    userComment.value = it
                }

                //Button
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {

                    TextButton(onClick = { onDismiss.invoke() }) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    TextButton(onClick = {

                        if (userComment.value.isNotEmpty() && userComment.value.isNotBlank()) {

                            if (NetworkChecker(context).internetConnection) {
                                onPositiveClicked.invoke(userComment.value)
                                onDismiss.invoke()
                            } else {
                                Toast.makeText(
                                    context,
                                    "connect to internet first...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

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

@Composable
fun CommentBody(comment: Comment) {

    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = CardDefaults.shape
    ) {

        Column(Modifier.padding(12.dp)) {

            Text(
                text = comment.userEmail,
                style = TextStyle(fontSize = 15.sp),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = comment.text,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(10.dp)
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(edtValue: String, hint: String, onValueChanges: (String) -> Unit) {

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = false,
        maxLines = 2,
        onValueChange = onValueChanges,
        placeholder = { Text("Write Something...") },
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = ShapeDefaults.Medium,
    )
}

@Composable
fun AddToCart() {


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainAppTheme {
        AddNewComment({} , {})

        }
    }
