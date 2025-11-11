package com.prototype.nhsappcv1.screens.accountsettings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.SoundEffectConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.prototype.nhsappcv1.components.ProfileCard
import com.prototype.nhsappcv1.ui.theme.*
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

    // State to track whether the card is flipped
    val isCardFlipped = remember { mutableStateOf(false) }

    // State to control bottom sheet visibility
    val showBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Callback to toggle flip state
    val onCardFlip: () -> Unit = {
        isCardFlipped.value = !isCardFlipped.value
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
                        color = nhsBlack
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            // Navigate to proxy access screen
                        }
                    ) {
                        Icon(
                            Icons.Outlined.SupervisedUserCircle,
                            contentDescription = null,
                            tint = nhsBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Proxy access",
                            color = nhsBlue,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey5
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
            // Profile Header Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 24.dp)
                        .padding(top = 16.dp, bottom = 24.dp)
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
                        // ProfileCard component with flip state callback
                        ProfileCard(
                            name = "John Smith",
                            nhsNumber = "123 456 7890",
                            dateOfBirth = "15 March 1985",
                            address = "123 High Street\nLondon, SW1A 1AA",
                            gpPractice = "Central Medical Centre",
                            bloodType = "O+",
                            isFlipped = isCardFlipped.value,
                            onFlip = onCardFlip
                        )

                        // Grouped action buttons
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Details button with bounce animation - only shown when card is flipped
                            AnimatedVisibility(
                                visible = isCardFlipped.value,
                                enter = fadeIn(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ) + expandVertically(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                        showBottomSheet.value = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = nhsBlue,
                                        containerColor = Color.Transparent
                                    ),
                                    border = BorderStroke(1.dp, nhsBlue),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        Icons.Outlined.Assignment,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text("View full details", style = MaterialTheme.typography.labelLarge)
                                }
                            }

                            // Copy and Share buttons row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, "NHS Number: 123 456 7890")
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
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
            }

            // Content sections below the profile card - always show basic settings
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 24.dp)
                ) {
                    // Basic Settings List - always visible
                    BasicSettingsList(view = view)

                    // Support Section (always visible)
                    Spacer(modifier = Modifier.height(24.dp))

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

        // Logout Alert Dialog
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

        // Bottom Sheet with Full Profile Details
        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = sheetState,
                containerColor = nhsGrey5,
                dragHandle = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(nhsGrey5)
                            .padding(top = 12.dp, bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(nhsGrey3)
                        )
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(nhsGrey5)
                ) {
                    // Custom Top App Bar
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = nhsGrey5,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Dismiss button
                            IconButton(
                                onClick = {
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                    scope.launch {
                                        sheetState.hide()
                                        showBottomSheet.value = false
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Close,
                                    contentDescription = "Dismiss",
                                    tint = nhsBlack
                                )
                            }

                            // Title
                            Text(
                                "Full Profile Details",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = nhsBlack
                            )

                            // Share button
                            IconButton(
                                onClick = {
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                    // Create comprehensive profile text
                                    val profileText = """
                                        NHS Profile
                                        
                                        Personal Information:
                                        Name: John Smith
                                        Age: 39 years old (Born: 15 March 1985)
                                        NHS Number: 123 456 7890
                                        Address: 123 High Street, London, SW1A 1AA
                                        Registered GP Practice: Central Medical Centre, 123 Medical Road, London
                                        Blood Type: O+
                                        
                                        Medical Information:
                                        Conditions: Type 2 Diabetes, Hypertension
                                        Allergies: Penicillin, Peanuts
                                        Medications: Metformin 500mg - Twice daily, Amlodipine 5mg - Once daily
                                    """.trimIndent()

                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, profileText)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share profile via"))
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Share,
                                    contentDescription = "Share",
                                    tint = nhsBlue
                                )
                            }
                        }
                    }

                    // Scrollable content
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp)
                            .padding(top = 16.dp)
                    ) {
                        item {
                            FullProfileInformationList(view = view)
                        }

                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BasicSettingsList(view: android.view.View) {
    // Settings Section Header
    Text(
        "Settings",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = nhsBlack,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )

    // Settings Card
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
                icon = Icons.Outlined.Security,
                title = "Security & privacy",
                subtitle = "Password, biometrics",
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

@Composable
private fun FullProfileInformationList(view: android.view.View) {
    // Personal Information Section
    Text(
        "Personal Information",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = nhsBlack,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )

    Card(
        modifier = Modifier.padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            ProfileDetailItem(
                icon = Icons.Outlined.Person,
                label = "Name",
                value = "John Smith",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Cake,
                label = "Age",
                value = "39 years old (Born: 15 March 1985)",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Badge,
                label = "NHS Number",
                value = "123 456 7890",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Home,
                label = "Address",
                value = "123 High Street\nLondon, SW1A 1AA",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.LocalHospital,
                label = "Registered GP Practice",
                value = "Central Medical Centre\n123 Medical Road, London",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Bloodtype,
                label = "Blood Type",
                value = "O+",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) },
                showDivider = false
            )
        }
    }

    // Medical Information Section
    Text(
        "Medical Information",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = nhsBlack,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )

    Card(
        modifier = Modifier.padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            ProfileDetailItem(
                icon = Icons.Outlined.HealthAndSafety,
                label = "Conditions",
                value = "Type 2 Diabetes\nHypertension",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Warning,
                label = "Allergies",
                value = "Penicillin\nPeanuts",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Medication,
                label = "Medications",
                value = "Metformin 500mg - Twice daily\nAmlodipine 5mg - Once daily",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.EventNote,
                label = "Recent Consultations",
                value = "GP Appointment - 15 Oct 2025\nBlood Test - 1 Oct 2025",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) }
            )
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileDetailItem(
                icon = Icons.Outlined.Vaccines,
                label = "Immunisations",
                value = "COVID-19 Booster - 20 Sep 2025\nInfluenza - 15 Sep 2025\nTetanus - 10 Jan 2022",
                onClick = { view.playSoundEffect(SoundEffectConstants.CLICK) },
                showDivider = false
            )
        }
    }
}

@Composable
private fun ProfileDetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit,
    showDivider: Boolean = true
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = nhsBlue,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    label,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium,
                    color = nhsGrey
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    value,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = nhsBlack
                )
            }
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