package com.prototype.demonhsapp.screens.messages.yourmessages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.Filter
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourMessages(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Your messages", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back to messages"
                        )
                    }
                },
                actions = {
                    HelpButton()
                    AccountButton()
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
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
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(values).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            Text("You 2 unread messages", fontSize = 16.sp)
                        }
                    }
                    //Filter
                    item() {

                        Column () {
                            Filter()
                        }
                    }
                    //Start of section
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = { navController.navigate(
                                        Routes.messageDetail)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Portland Street Great Westwood Surgery", modifier = Modifier.padding( top = 16.dp, bottom = 8.dp), fontWeight = FontWeight.SemiBold) },
                                    leadingContent = { Badge { Text("") } },
                                    overlineContent = { Text("1:02pm", fontWeight = FontWeight.SemiBold)  },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = { Text("Patient survey reminder. The patient feedback survey is about... ", modifier = Modifier.padding(bottom = 8.dp)) }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Portland Street Great Westood Surgery", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { Badge { Text("") } },
                                    overlineContent = { Text("Thursday") },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = { Text("Dear Mary, we would like to ask you a few questions about...", modifier = Modifier.padding(bottom = 8.dp)) }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Hughenden Valley Surgery", modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { Text("4 Oct 2023") },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = { Text(" Reminder of your telephone appointment with your GP on... ", modifier = Modifier.padding(bottom = 8.dp)) }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("NHS App", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = {  },
                                    overlineContent = { Text("18 Sep 2023") },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { Text("Your next COVID-19 vaccination. I'd like to invite you to get your...") }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }



                }
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun MessagesPreview(){
    YourMessages(rememberNavController())
}