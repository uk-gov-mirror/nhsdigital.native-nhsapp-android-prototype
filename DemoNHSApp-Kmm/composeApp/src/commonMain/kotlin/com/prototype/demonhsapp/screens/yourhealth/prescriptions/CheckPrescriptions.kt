package com.prototype.demonhsapp.screens.yourhealth.prescriptions


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsGreenTint
import com.prototype.demonhsapp.ui.theme.nhsGreenTone
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckPrescriptions(navController: NavController, modifier: Modifier) {
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Check if your prescriptions are ready", maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
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
                            Text("Find out if your prescription is ready to collect. If you need your medicine urgently, contact your pharmacy", fontSize = 16.sp)
                        }
                    }

                    //Start of list
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.prescriptionsDetail)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("One off prescription", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = {
                                        Card(colors = CardDefaults.cardColors(
                                        nhsGreenTint
                                    )) { Text("Ready to collect", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsGreenTone) }
                                                      },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = {
                                        Column {
                                            Text("Cephalexin 500mg capsules\r\nCodeine phosphate 30mg tablets", modifier = Modifier.padding(bottom = 8.dp))
                                            Text("Prescribed on 18 Jul 2024", modifier = Modifier.padding(bottom = 8.dp), color = nhsGrey)
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Badge()
                                                Text("1 item cancelled", modifier = Modifier.padding(start = 8.dp))
                                            }

                                        }
                                    }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }

                        }
                    }
                    // Past prescriptions
                    item() {
                        Divider(modifier = Modifier.padding(bottom = 16.dp))
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.pastPrescriptions)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Past prescriptions", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = {  }

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

@Preview(backgroundColor = 0xFFF0F4F5)
@Composable
fun CheckPrescriptionsPreview(){
    CheckPrescriptions(rememberNavController(), modifier = Modifier)
}