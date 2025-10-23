package com.prototype.demonhsapp.screens.home

import android.view.SoundEffectConstants
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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
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
import com.prototype.demonhsapp.components.CampaignCard
import com.prototype.demonhsapp.components.ChromeCustomTab
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
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Home", maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    Column (modifier = Modifier
                        .padding(start = 16.dp)
                        .height(22.dp)
                        .width(54.dp)) {
                        Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
                    }
                },
                actions = {
                    HelpButton()
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey4.copy(alpha = 0.2f)),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {

                LazyColumn(
                    modifier = Modifier
                        .padding(values)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // NHS logo and welcome message
//                    item() {
//
//                        Column (modifier = Modifier
//                            .padding(bottom = 24.dp)
//                            .height(22.dp)
//                            .width(54.dp)) {
//                            Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
//                        }
//                    }
//                    item() {
//
//                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
//                            Text("Good evening,", fontSize = 18.sp)
//                            Text("Mary Swanson", fontSize = 32.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
//                            Row {
//                                Text("NHS number: ", fontSize = 18.sp, color = nhsGrey)
//                                Text("123 456 7890", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue)
//                            }
//                        }
//                    }
                    //Section for the main menu
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.prescriptions)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
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
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
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
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.prescriptions2)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
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
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.upcomingAndAastAppointments)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
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

                    //Section title for nhs information and support
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("NHS information and support", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List customTabs
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ChromeCustomTab()
                            }
                        }
                    }

                    //Start of section [Campaign card]
                    item() {

                        Column(modifier = Modifier.padding(bottom = 16.dp)) { CampaignCard() }
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