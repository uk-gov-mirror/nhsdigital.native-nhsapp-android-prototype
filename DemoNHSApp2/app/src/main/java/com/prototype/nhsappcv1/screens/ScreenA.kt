package com.prototype.nhsappcv1.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prototype.nhsappcv1.navigation.Routes

@Composable
fun ScreenA(navController: NavController, modifier: Modifier) {
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Screen A")
        Button(onClick = { navController.navigate(Routes.yourHealth)}) { Text("Go to Screen B")}
    }
}

