package com.prototype.demonhsapp.screens.messages

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.components.MessagesWebView
import com.prototype.demonhsapp.components.ProfileWebView
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Messages(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("", maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(
//                            imageVector = Icons.Filled.Menu,
//                            contentDescription = "Localized description"
//                        )
//                    }
                },
                actions = {
                    HelpButton()
                    AccountButton()
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey4.copy(alpha = 0.2f)),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
//            NavigationBar(containerColor = nhsBlue, modifier = Modifier) {
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.Home, contentDescription = null)}, label = {Text("Home")}, selected = false, onClick = {navController.navigate(Routes.home)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null)}, label = {Text("Services")}, selected = false, onClick = {navController.navigate(Routes.services)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)}, label = {Text("Your health")}, selected = false, onClick = {navController.navigate(Routes.yourHealth)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = { BadgedBox(badge = { Badge{ Text("2", modifier = Modifier.semantics(){contentDescription = "8 new notifications"}) } }) {Icon(imageVector = Icons.Default.Email, contentDescription = null)} }, label = {Text("Messages")}, selected = true, onClick = {navController.navigate(Routes.messages)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//
//            }
        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize().padding(top = 112.dp)) {
                MessagesWebView()
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun MessagesPreview(){
    Messages(rememberNavController(), modifier = Modifier)
}