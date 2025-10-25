package com.prototype.demonhsapp.screens.accountsettings

import android.view.SoundEffectConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettings(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val showAlertDialog = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = (LocalConfiguration.current.screenHeightDp / 2).dp,
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(nhsBlack, RoundedCornerShape(2.dp))
                )
            }
        },
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(nhsGrey5)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Account Section
                item {
                    Text(
                        "Account",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column {
                            ProfileListItem(
                                icon = Icons.Outlined.AccountCircle,
                                title = "Personal information",
                                subtitle = "Name, date of birth, contact details",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            ProfileListItem(
                                icon = Icons.Outlined.Lock,
                                title = "Security",
                                subtitle = "Password, biometric login",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            ProfileListItem(
                                icon = Icons.Outlined.SupervisedUserCircle,
                                title = "Manage proxy access",
                                subtitle = "Services for another person",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) },
                                showDivider = false
                            )
                        }
                    }
                }

                // Preferences Section
                item {
                    Text(
                        "Preferences",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column {
                            ProfileListItem(
                                icon = Icons.Outlined.Notifications,
                                title = "Notifications",
                                subtitle = "Manage your alerts",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            ProfileListItem(
                                icon = Icons.Outlined.Language,
                                title = "Language & region",
                                subtitle = "English (UK)",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) },
                                showDivider = false
                            )
                        }
                    }
                }

                // Support Section
                item {
                    Text(
                        "Support",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                item {
                    Card(
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column {
                            ProfileListItem(
                                icon = Icons.Outlined.Help,
                                title = "Help & support",
                                subtitle = "FAQs and contact",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            ProfileListItem(
                                icon = Icons.Outlined.PrivacyTip,
                                title = "Privacy & legal",
                                subtitle = "Terms, privacy policy, cookies",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            ProfileListItem(
                                icon = Icons.Outlined.Info,
                                title = "About",
                                subtitle = "Version 2.36.6",
                                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) },
                                showDivider = false
                            )
                        }
                    }
                }

                // Log out button
                item {
                    OutlinedButton(
                        onClick = { showAlertDialog.value = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = nhsBlue,
                            containerColor = Color.Transparent
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = nhsBlue
                        ),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            Icons.Outlined.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Log out",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        },
        sheetContainerColor = nhsGrey5,
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
    ) { paddingValues ->
        // Main content - Profile header with gradient
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(nhsLightBlue, nhsBlue)
                    )
                )
                .padding(paddingValues),
            contentPadding = PaddingValues(top = 64.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Profile",
                            tint = nhsBlue,
                            modifier = Modifier.size(56.dp)
                        )
                    }

                    // Name
                    Text(
                        "John Smith",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Medium,
                        color = nhsDarkBlue
                    )

                    // NHS Number
                    Text(
                        "NHS Number: 123 456 7890",
                        style = MaterialTheme.typography.bodyLarge,
                        color = nhsDarkBlue
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (showAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { showAlertDialog.value = false },
                containerColor = Color.White,
                icon = {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = nhsBlue
                    )
                },
                title = {
                    Text(
                        text = "Log out?",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text("You'll need to log in again to access your health records and services.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showAlertDialog.value = false
                            // Handle logout
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)
                    ) {
                        Text("Log out")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showAlertDialog.value = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = nhsGrey)
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileListItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = nhsGrey,
                modifier = Modifier.size(24.dp)
            )
        },
        headlineContent = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                color = nhsBlack
            )
        },
        supportingContent = {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = nhsGrey
            )
        },
        trailingContent = {
            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = nhsGrey2,
                modifier = Modifier.size(20.dp)
            )
        }
    )
}

@Preview(showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun AccountSettingsPreview() {
    AccountSettings(rememberNavController(), modifier = Modifier)
}