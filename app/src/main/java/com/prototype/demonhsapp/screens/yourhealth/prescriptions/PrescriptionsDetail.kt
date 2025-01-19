package com.prototype.demonhsapp.screens.yourhealth.prescriptions


import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.prototype.demonhsapp.components.Barcode
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.ui.theme.nhsGreenTint
import com.prototype.demonhsapp.ui.theme.nhsGreenTone
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import com.prototype.demonhsapp.ui.theme.nhsRedTint
import com.prototype.demonhsapp.ui.theme.nhsRedTone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionsDetail(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Your prescription", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
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
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
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
                    //Barcode
                    item() {

                        Column () {
                            Barcode()
                        }
                    }

                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text("Medicines", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    //Start of list
                    item() {
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = {
                                /*TODO*/
                                }),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Cephalexin 500mg capsules", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = {
                                    Card(
                                        colors = CardDefaults.cardColors(nhsGreenTint)
                                    ) { Text("Ready to collect", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsGreenTone) }
                                },
                                trailingContent = { },
                                supportingContent = {
                                    Text("Quantity: 28 capsules", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Codeine phosphate 30mg tablets", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = {
                                    Card(
                                        colors = CardDefaults.cardColors(nhsRedTint)
                                    ) { Text("Cancelled", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsRedTone) }
                                },
                                trailingContent = { },
                                supportingContent = {
                                    Text("Quantity: 10 capsules", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("For instructions on how to take your medicine, read the label on the packet or container.")
                        }

                    }

                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    //Start of list
                    item() {
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Pharmarcy", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = { },
                                trailingContent = { },
                                supportingContent = {
                                    Text("Boots Pharmacy\r\nBalham\r\nLondon\r\nSW12 73G\r\n080867766155", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Date prescribed", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = { },
                                trailingContent = { },
                                supportingContent = {
                                    Text("18 July 2024", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Prescribed by", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = { },
                                trailingContent = { },
                                supportingContent = {
                                    Text("Dr. Smith", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                        Column () {
                            ListItem(
                                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                colors = ListItemDefaults.colors(Color.Transparent) ,
                                headlineContent = { Text("Organisation", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                overlineContent = { },
                                trailingContent = { },
                                supportingContent = {
                                    Text("York Road Practice", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                }

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }

                    }



                }
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun PrescriptionsDetailPreview(){
    PrescriptionsDetail(rememberNavController(), modifier = Modifier)
}