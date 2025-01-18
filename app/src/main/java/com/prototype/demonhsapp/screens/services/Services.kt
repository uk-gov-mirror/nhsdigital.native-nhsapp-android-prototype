package com.prototype.demonhsapp.screens.services


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
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
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Services(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Services", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
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
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.Home, contentDescription = null)}, label = {Text("Home")}, selected = false, onClick = {navController.navigate(Routes.home)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Default.LocalHospital, contentDescription = null)}, label = {Text("Services")}, selected = true, onClick = {navController.navigate(Routes.services)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)}, label = {Text("Your health")}, selected = false, onClick = {navController.navigate(Routes.yourHealth)}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
//                NavigationBarItem(icon = { BadgedBox(badge = { Badge{ Text("2", modifier = Modifier.semantics(){contentDescription = "8 new notifications"}) } }) {Icon(imageVector = Icons.Outlined.Email, contentDescription = null)} }, label = {Text("Messages")}, selected = false, onClick = {navController.navigate(Routes.messages)}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
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
                            Text("Get care and support to help you stay well", fontSize = 16.sp)
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text("Your GP services", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        }
                    }
                    //Start of list
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {navController.navigate(
                                        Routes.prescriptions)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Request medicines", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Contact your GP surgery for a document or update", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Check for available GP appointments", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
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
                            Text("Other NHS services", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        }
                    }
                    //Start of list
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Check if you need urgent medical help using 111 online", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Find NHS services near you", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
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
                            Text("Find health information", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        }
                    }
                    // Start of list
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Browse NHS health information", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Browse NHS medicines information", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Find COVID-19 guidance", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

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
fun ServicesPreview(){
    Services(rememberNavController(), modifier = Modifier)
}