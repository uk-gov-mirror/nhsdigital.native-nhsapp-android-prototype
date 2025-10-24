package com.nhs.online.nhsonline.proto

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nhs.online.nhsonline.proto.health.HealthScreen
import com.nhs.online.nhsonline.proto.home.HomeScreen
import com.nhs.online.nhsonline.proto.messages.MessagesScreen
import com.nhs.online.nhsonline.proto.navigation.BottomBar
import com.nhs.online.nhsonline.proto.navigation.BottomNavItem
import com.nhs.online.nhsonline.proto.services.ServicesScreen
import com.nhs.online.nhsonline.proto.ui.theme.NHSAppTheme

@Composable
fun NHSAppScreen(){
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
        AppNavHost(navController = navController)
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NHSAppTheme {
        NHSAppScreen()
    }
}
@Composable
private fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Services.route) { ServicesScreen() }
        composable(BottomNavItem.YourHealth.route) { HealthScreen() }
        composable(BottomNavItem.Messages.route) { MessagesScreen() }
    }
}

