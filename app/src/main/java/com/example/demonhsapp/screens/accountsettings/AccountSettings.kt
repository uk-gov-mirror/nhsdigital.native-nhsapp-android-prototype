package com.example.demonhsapp.screens.accountsettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demonhsapp.components.AccountButton
import com.example.demonhsapp.components.ButtonPrimary
import com.example.demonhsapp.components.ButtonSecondary
import com.example.demonhsapp.components.HelpButton
import com.example.demonhsapp.navigation.Routes
import com.example.demonhsapp.ui.theme.nhsBlue
import com.example.demonhsapp.ui.theme.nhsGrey
import com.example.demonhsapp.ui.theme.nhsGrey2
import com.example.demonhsapp.ui.theme.nhsGrey4
import com.example.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettings(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val showAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { },
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
                            Text("Change your NHS App settings and personal details", fontSize = 16.sp)
                        }
                    }
                    //Start of section
                    item() {
                        Card (Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*do something*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Manage services for another person", modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = {  },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = {  }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Change your contact or login details", modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = {  }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Turn on fingerprint, face or iris login", modifier = Modifier.padding( top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding( top = 16.dp, bottom = 8.dp))},
                                    // To-Do: Fix bullet point formatting
                                    supportingContent = {  }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Manage notifications", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Manage cookies and read legal information", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Update your GP surgery on changes to your personal details", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Logout
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            ButtonSecondary("Log out", onClick = {showAlertDialog.value = true })
                            Text("Version 2.36.6(2.36.0)", color = nhsGrey)
                        }

                    }


                }
            }

            if (showAlertDialog.value){
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onDismissRequest.
                        showAlertDialog.value = false
                    },
                    containerColor = nhsGrey5,
                    tonalElevation = 24.dp,
                    icon = { Icon(Icons.Outlined.Info, contentDescription = null, tint = nhsGrey) },
                    title = { Text(text = "Are you sure you want to log out?") },
                    text = {
                        Text("All unsaved progress will be lost")
                    },
                    confirmButton = {
                        TextButton(onClick = { showAlertDialog.value = false }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) { Text("Confirm", fontSize = 16.sp) }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAlertDialog.value = false }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) { Text("Dismiss", fontSize = 16.sp) }
                    }
                )
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun AccountSettingsPreview(){
    AccountSettings(rememberNavController(), modifier = Modifier)
}