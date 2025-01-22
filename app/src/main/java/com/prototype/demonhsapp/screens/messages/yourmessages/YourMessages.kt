package com.prototype.demonhsapp.screens.messages.yourmessages

import android.view.SoundEffectConstants
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
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.prototype.demonhsapp.components.Filter
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourMessages(navController: NavController) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Your messages", maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                    }) {
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
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey4.copy(alpha = 0.2f)),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { },
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
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.messageDetail)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Portland Street Great Westwood Surgery", fontSize = 18.sp, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp), fontWeight = FontWeight.SemiBold) },
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
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Portland Street Great Westood Surgery", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
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
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Hughenden Valley Surgery", fontSize = 18.sp, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
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
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("NHS App", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
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