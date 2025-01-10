package com.example.demonhsapp.screens.yourhealth.prescriptions


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demonhsapp.components.AccountButton
import com.example.demonhsapp.components.ButtonSecondary
import com.example.demonhsapp.components.HelpButton
import com.example.demonhsapp.components.PrescriptionDetailModal
import com.example.demonhsapp.components.ServicesCard
import com.example.demonhsapp.navigation.Routes
import com.example.demonhsapp.ui.theme.nhsBlack
import com.example.demonhsapp.ui.theme.nhsBlue
import com.example.demonhsapp.ui.theme.nhsGreenTint
import com.example.demonhsapp.ui.theme.nhsGreenTone
import com.example.demonhsapp.ui.theme.nhsGrey
import com.example.demonhsapp.ui.theme.nhsGrey2
import com.example.demonhsapp.ui.theme.nhsGrey4
import com.example.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckPrescriptions(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Check if your prescriptions are ready", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                                    modifier = Modifier.clickable(onClick = { navController.navigate(Routes.prescriptionsDetail)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("One off prescription", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
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
                                    modifier = Modifier.clickable(onClick = { navController.navigate(Routes.pastPrescriptions)}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Past prescriptions", modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
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

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun CheckPrescriptionsPreview(){
    CheckPrescriptions(rememberNavController(), modifier = Modifier)
}