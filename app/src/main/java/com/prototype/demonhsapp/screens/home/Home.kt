package com.prototype.demonhsapp.screens.home

import androidx.compose.foundation.Image
import com.prototype.demonhsapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.CampaignCard
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.components.WebViewButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
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
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
//            NavigationBar(containerColor = nhsBlue, modifier = Modifier) {
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}, label = {Text("Home")}, selected = true, onClick = {navController.navigate(Routes.home)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null)}, label = {Text("Services")}, selected = false, onClick = {navController.navigate(Routes.services)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)}, label = {Text("Your health")}, selected = false, onClick = {navController.navigate(Routes.yourHealth)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = { BadgedBox(badge = { Badge{ Text("2", modifier = Modifier.semantics(){contentDescription = "8 new notifications"}) } }) {Icon(imageVector = Icons.Outlined.Email, contentDescription = null)}}, label = {Text("Messages")}, selected = false, onClick = {navController.navigate(Routes.messages)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//
//            }
        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(values)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // NHS logo and welcome message
                    item() {

                        Column (modifier = Modifier
                            .padding(bottom = 24.dp)
                            .height(22.dp)
                            .width(54.dp)) {
                            Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
                        }
                    }
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            Text("Good evening,", fontSize = 18.sp)
                            Text("Mary Swanson", fontSize = 32.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                            Row {
                                Text("NHS number: ", fontSize = 18.sp, color = nhsGrey)
                                Text("123 456 7890", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue)
                            }
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text("Services", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {navController.navigate(Routes.prescriptions)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Request medicines", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                WebViewButton()
                            }
                        }
                    }

                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Your health", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("GP health record", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {navController.navigate(Routes.prescriptions2)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Prescriptions", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {navController.navigate(
                                        Routes.upcomingAndAastAppointments)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Upcoming and past appointments", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Messages", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {navController.navigate(Routes.yourMessages)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("View your messages", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { Icon(Icons.Outlined.Email, contentDescription = null) },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2) },
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Account", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Manage services for another person", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { BadgedBox( badge = {Badge(content = { Text("2") })} ){ Icon(Icons.Outlined.SupervisedUserCircle, contentDescription = null)  } },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2) },
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Start of section [Campaign card]
                    item() {

                        Column(modifier = Modifier.padding(bottom = 16.dp)) { CampaignCard() }
                    }
                    item() {

                        TextButton(onClick = { /*TODO*/}, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                            Row (verticalAlignment = Alignment.CenterVertically) { Icon(imageVector = Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = null)
                                Text("Get help using the NHS App", modifier = Modifier.padding(start = 8.dp), fontSize = 18.sp,)
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
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}