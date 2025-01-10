package com.example.demonhsapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.demonhsapp.screens.home.Home
import com.example.demonhsapp.ui.theme.nhsBlue
import com.example.demonhsapp.ui.theme.nhsGrey
import androidx.navigation.compose.rememberNavController
import com.example.demonhsapp.navigation.Routes
import com.example.demonhsapp.screens.messages.Messages
import com.example.demonhsapp.screens.services.Services
import com.example.demonhsapp.screens.yourhealth.YourHealth

// These are examples with visual properties of the navigation bar. To see a full implementation with a navigation graph, see 'AppNavigation.kt' file

//// Custom Navigation bar
@Preview
@Composable
private fun ExampleNHSBottomNavigation(modifier: Modifier = Modifier) {
    // Implement composable here
    NavigationBar(containerColor = nhsBlue, modifier = modifier) {
        NavigationBarItem(icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}, label = {Text("Home")}, selected = true, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
        NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null)}, label = {Text("Services")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
        NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)}, label = {Text("Your health")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
        NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.Email, contentDescription = null)}, label = {Text("Messages")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))

    }
}




//@Composable
//fun NHSBottomNavigation(navController: NavController){
//
//    val navItemList = listOf(
//        navItem ("Home", Icons.Default.Home, Icons.Outlined.Home),
//        navItem("Services", Icons.Default.LocalHospital, Icons.Outlined.LocalHospital),
//        navItem("Your health", Icons.Default.Favorite, Icons.Outlined.FavoriteBorder),
//        navItem("Messages", Icons.Default.Email, Icons.Outlined.Email)
//    )
//
//    var selectedIndex by remember { mutableIntStateOf(0) }
//
//    Scaffold(
//        topBar = { },
//        bottomBar = {
//            NavigationBar(containerColor = nhsBlue) {
//                navItemList.forEachIndexed { index, navItem ->
//                    NavigationBarItem(
//                        selected = selectedIndex == index,
//                        onClick = { selectedIndex = index },
//                        icon = { Icon(imageVector = if (index == selectedIndex) {navItem.selectedIcon} else navItem.unselectedIcon, contentDescription = null)},
//                        label = { Text(text = navItem.label)},
//                        colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey)
//                    )
//                }
//            }
//        },
//        content = { values ->
//            NavView(modifier = Modifier.padding(values), selectedIndex)
//        }
//    )
//}

@Composable
private fun NavView(modifier: Modifier, selectedIndex: Int){
    //Content goes here
    when(selectedIndex){
        0-> Home(rememberNavController(), modifier)
        1-> Services(rememberNavController(), modifier)
        2-> YourHealth(rememberNavController(), modifier)
        3-> Messages(rememberNavController(), modifier)
    }
}


@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun NavBarPreview() {
    ExampleNHSBottomNavigation()
}