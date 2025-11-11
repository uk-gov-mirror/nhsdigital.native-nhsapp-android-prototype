package com.prototype.nhsappdv1.screens.yourhealth.prescriptions


import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.components.AccountButton
import com.prototype.nhsappdv1.components.ButtonSecondary
import com.prototype.nhsappdv1.components.HelpButton
import com.prototype.nhsappdv1.navigation.Routes
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey2
import com.prototype.nhsappdv1.ui.theme.nhsGrey4
import com.prototype.nhsappdv1.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewManagePrescriptions(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("View and manage prescriptions", maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back to previous screen"
                        )
                    }
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

        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(values).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            Text("Check your prescriptions and choose the pharmarcy they are collected or delivered from", fontSize = 16.sp)
                        }
                    }

                    //Start of list
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Requested medicines", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { Text("Check if your GP has approved the medicines you requested in the app ", modifier = Modifier.padding(bottom = 8.dp)) }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.checkPrescriptions)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Check if your prescriptions are ready", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { Badge(content = {Text("1")}) },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { Text("View prescriptions being prepared by the pharmacy ", modifier = Modifier.padding(bottom = 8.dp)) }

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
                                    headlineContent = { Text("Choose a pharmacy", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))},
                                    supportingContent = { Text("Choose or change a pharmacy for your prescriptions to be collected or delivered from ", modifier = Modifier.padding(bottom = 8.dp)) }

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
                                    headlineContent = { Text("Hospital and other medicines", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))},
                                    supportingContent = { Text("View your current and past medicines ", modifier = Modifier.padding(bottom = 8.dp)) }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    // Services card
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column (Modifier.padding(bottom = 16.dp)) {
                                Card(onClick = {/*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {

                                    Column(modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null, modifier = Modifier.padding(bottom = 8.dp), tint = nhsBlue)
                                        Text("Access services", modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                                        Text("Request medicines you take often", modifier = Modifier.padding(bottom = 8.dp),  textAlign = TextAlign.Center, fontSize = 16.sp, color = nhsBlack)
                                        ButtonSecondary("Request medicines") { }
                                    }


                                }
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
fun ViewManagePrescriptionsPreview(){
    ViewManagePrescriptions(rememberNavController(), modifier = Modifier)
}