package com.example.bookshop.ui.screens.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookshop.R
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.screens.login.data.SignUpObject
import com.example.bookshop.ui.theme.Transparent
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.siddroid.holi.colors.MaterialColor

@Composable
fun LogInScreen(
    navController: NavController,
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit
) {
    val auth = remember { Firebase.auth }
    var errorState by remember{ mutableStateOf("") }
    var email by remember { mutableStateOf("novichikhina.05@mail.ru") }
    var password by remember { mutableStateOf("123456") }
    var passwordVisible by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(id = R.drawable.mainscreen), contentDescription = "mainPhoto",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                "Sign in to your Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, start = 10.dp, end = 50.dp),
                letterSpacing = 1.sp,
                lineHeight = 40.sp
            )
            RoundedTextField(
                text = email,
                onValueChange = {
                    email = it
                },
                label = "Email"
            )
            RoundedTextField(
                text = password,
                onValueChange ={
                    password = it
                },
                label = "Password"
            )
            if(errorState.isNotEmpty()) {
                Text(
                    text = errorState,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    text = "Forgot Password?",
                    color = MaterialColor.BLUE_A100,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }
            LogInButton(
                text = "Sign In"
            ) {
                signIn(auth, email, password,
                    onSignInFailure = {

                },
                    onSignInSuccess = {navData->
                        onNavigateToMainScreen(navData)
                    })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Divider(
                    modifier = Modifier
                        .weight(1F)
                        .height(1.dp),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White,
                    text = "Or"
                )
                Divider(
                    modifier = Modifier
                        .weight(1F)
                        .height(1.dp),
                    color = Color.White
                )
            }
            LogInButton(
                text = "Continue with Google"
            ) {

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(SignUpObject)
                        }
                        .padding(start = 7.dp),
                    text = "Sign Up",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialColor.BLUE_A100,
                )
            }
        }
    }
}

private fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInFailure: (String) -> Unit,
    onSignInSuccess: (MainScreenDataObject) -> Unit
){
    if(email.isBlank() || password.isBlank()){
        onSignInFailure("Email and Password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener { exception ->
            onSignInFailure(exception.message ?: "Sign Up Error")
        }
}