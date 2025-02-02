package com.example.bookshop.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.bookshop.R
import com.example.bookshop.ui.screens.login.LogInButton
import com.example.bookshop.ui.screens.login.RoundedTextField
import com.example.bookshop.ui.screens.login.data.MainScreenDataObject
import com.example.bookshop.ui.theme.Transparent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.siddroid.holi.colors.MaterialColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit
) {
    val auth = Firebase.auth
    var passwordVisible by remember { mutableStateOf(false) }

    var email by remember{ mutableStateOf("") }
    var errorState by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }

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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = {
                    onBack()
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Text(
                "Sign up",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
            RoundedTextField(
                text = email,
                label = "Email",
                onValueChange = {
                    email = it
                }
            )
            RoundedTextField(
                text = password,
                label = "Password",
                onValueChange = {
                    password = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            if(errorState.isNotEmpty()) {
                Text(
                    text = errorState,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            LogInButton(
                text = "Sign Up",
                onClick = {
                    signUp(auth, email, password,
                        onSignUpFailure = { error ->
                            Log.d("MyLog","Sign Up : $error")
                    }, onSignUpSuccess = {navData->
                        onNavigateToMainScreen(navData)
                    })
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(start = 7.dp),
                    text = "Login",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialColor.BLUE_A100,
                )
            }
        }
    }
}
private fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpFailure: (String) -> Unit,
    onSignUpSuccess: (MainScreenDataObject) -> Unit
){
    if(email.isBlank() || password.isBlank()){
        onSignUpFailure("Email and Password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!,
                    )
                )
            }
        }
        .addOnFailureListener { exception ->
            onSignUpFailure(exception.message ?: "Sign Up Error")
        }
}

