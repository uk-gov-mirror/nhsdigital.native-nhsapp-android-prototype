package com.prototype.nhsappdv1.screens.accountsettings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.SoundEffectConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettings(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val context = LocalContext.current
    val showAlertDialog = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Calculate if we should show grey background based on scroll
    val showGreyBackground = remember {
        derivedStateOf {
            // Check if we've scrolled past the first item (profile section)
            listState.firstVisibleItemIndex > 0 ||
                    (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset > 300)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Profile",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        color = if (showGreyBackground.value) nhsBlack else nhsDarkBlue
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = if (showGreyBackground.value) nhsGrey5 else nhsLightBlue,
                    scrolledContainerColor = if (showGreyBackground.value) nhsGrey5 else nhsLightBlue
                ),
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        containerColor = nhsGrey5
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Profile Header Section with Light Blue Background
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsLightBlue)
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 48.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                // Parallax effect - content moves slower than scroll
                                val scrollOffset = if (listState.firstVisibleItemIndex == 0) {
                                    listState.firstVisibleItemScrollOffset.toFloat()
                                } else {
                                    0f
                                }
                                translationY = scrollOffset * 0.5f

                                // Fade out effect as you scroll
                                alpha = (1f - (scrollOffset / 500f)).coerceIn(0f, 1f)

                                // Scale down effect - content shrinks as you scroll
                                val scale = (1f - (scrollOffset / 1000f)).coerceIn(0.8f, 1f)
                                scaleX = scale
                                scaleY = scale
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Avatar - 3D Glassmorphic Circle
                        Box(
                            modifier = Modifier.size(112.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Outer glow/shadow rings
                            Box(
                                modifier = Modifier
                                    .size(112.dp)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                nhsBlue.copy(alpha = 0.2f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )

                            // Main 3D container with elevated shadow
                            Box(
                                modifier = Modifier
                                    .size(96.dp)
                                    .graphicsLayer {
                                        shadowElevation = 12.dp.toPx()
                                        shape = CircleShape
                                        clip = true
                                        ambientShadowColor = nhsBlue
                                        spotShadowColor = nhsBlue
                                    }
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFFF5F8FA),
                                                Color.White
                                            ),
                                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                            end = androidx.compose.ui.geometry.Offset(300f, 300f)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                // Inner shadow/depth ring
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.03f),
                                                    Color.Black.copy(alpha = 0.08f)
                                                ),
                                                center = androidx.compose.ui.geometry.Offset(150f, 150f),
                                                radius = 200f
                                            )
                                        )
                                )

                                // Glassmorphic frosted overlay
                                Box(
                                    modifier = Modifier
                                        .size(92.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    Color.White.copy(alpha = 0.5f),
                                                    Color.White.copy(alpha = 0.2f)
                                                ),
                                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                                end = androidx.compose.ui.geometry.Offset(200f, 200f)
                                            )
                                        )
                                )

                                // Subtle border ring for definition
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    nhsBlue.copy(alpha = 0.15f),
                                                    Color.Transparent,
                                                    nhsBlue.copy(alpha = 0.1f)
                                                ),
                                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                                end = androidx.compose.ui.geometry.Offset(300f, 300f)
                                            )
                                        )
                                        .padding(1.dp)
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                )

                                // Icon centered
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = "Profile",
                                    tint = nhsBlue,
                                    modifier = Modifier.size(56.dp)
                                )

                                // Top shine/highlight for glossy 3D effect
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .offset(y = (-8).dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.White.copy(alpha = 0.7f),
                                                    Color.White.copy(alpha = 0.3f),
                                                    Color.Transparent,
                                                    Color.Transparent
                                                ),
                                                startY = 0f,
                                                endY = 120f
                                            )
                                        )
                                )

                                // Bottom shadow for more depth
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .offset(y = 8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Transparent,
                                                    Color.Black.copy(alpha = 0.05f),
                                                    Color.Black.copy(alpha = 0.1f)
                                                ),
                                                startY = 0f,
                                                endY = 300f
                                            )
                                        )
                                )
                            }
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

                        // Grouped action buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Copy button
                            OutlinedButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("NHS Number", "123 456 7890")
                                    clipboard.setPrimaryClip(clip)

                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "NHS number copied to clipboard",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = nhsBlue,
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(1.dp, nhsBlue),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.ContentCopy,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Copy", style = MaterialTheme.typography.labelLarge)
                            }

                            // Share button
                            OutlinedButton(
                                onClick = {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "My NHS Number: 123 456 7890")
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, "Share NHS Number")
                                    context.startActivity(shareIntent)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = nhsBlue,
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(1.dp, nhsBlue),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.Share,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Share", style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }
                }
            }

            // Grey Background Section Starts
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    // Account Section Header
                    Text(
                        "Account",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )

                    // Account Card
                    Card(
                        modifier = Modifier.padding(bottom = 16.dp),
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

                    // Preferences Section Header
                    Text(
                        "Preferences",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )

                    // Preferences Card
                    Card(
                        modifier = Modifier.padding(bottom = 16.dp),
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

                    // Support Section Header
                    Text(
                        "Support",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = nhsBlack,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                    )

                    // Support Card
                    Card(
                        modifier = Modifier.padding(bottom = 16.dp),
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

                    // Log out button
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

                    // Bottom spacing
                    Spacer(modifier = Modifier.height(32.dp))
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