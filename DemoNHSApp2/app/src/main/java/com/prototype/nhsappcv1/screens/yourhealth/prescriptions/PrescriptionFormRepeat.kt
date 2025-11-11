package com.prototype.nhsappcv1.screens.yourhealth.prescriptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.R
import com.prototype.nhsappcv1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionFormRepeat(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Full-screen dialog state for start screen
    var showStartDialog by remember { mutableStateOf(true) }

    // About prescriptions bottom sheet state
    val aboutSheetState = rememberModalBottomSheetState()
    var showAboutSheet by remember { mutableStateOf(false) }

    // Discard confirmation dialog state
    var showDiscardDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Request repeat prescription",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        color = nhsBlack
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { showDiscardDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Dismiss",
                            tint = nhsBlack
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
        bottomBar = {
            BottomAppBar(
                containerColor = nhsGrey5,
                tonalElevation = 0.dp
            ) {
                Button(
                    onClick = { /* TODO: Submit prescription request */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = nhsGreen
                    )
                ) {
                    Text(
                        text = "Submit request",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        containerColor = nhsGrey5
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Summary section header
            item {
                Text(
                    text = "Review your request",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = nhsBlack
                )
            }

            // Summary list card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color.White
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Pharmacy
                        SummaryListItem(
                            label = "Pharmacy",
                            trailingButtonText = "Add",
                            onTrailingButtonClick = { /* TODO: Add pharmacy */ },
                            showDivider = true
                        )

                        // Medicines requested
                        SummaryListItem(
                            label = "Medicines requested",
                            trailingButtonText = "Add",
                            onTrailingButtonClick = { /* TODO: Add medicines */ },
                            showDivider = true
                        )

                        // Additional information
                        SummaryListItem(
                            label = "Additional information",
                            trailingButtonText = "Add",
                            onTrailingButtonClick = { /* TODO: Add additional info */ },
                            showDivider = false
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Discard confirmation dialog
    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            containerColor = nhsGrey5,
            title = {
                Text(
                    text = "Discard request?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = nhsBlack
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Are you sure you want to leave? Your progress will be lost.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsBlack
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Vertically arranged text buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Continue button
                        TextButton(
                            onClick = { showDiscardDialog = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Continue",
                                color = nhsBlue,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        // Save as draft button
                        TextButton(
                            onClick = {
                                showDiscardDialog = false
                                // TODO: Save as draft functionality
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Save as draft",
                                color = nhsBlue,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        // Discard button
                        TextButton(
                            onClick = {
                                showDiscardDialog = false
                                navController.popBackStack()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Discard",
                                color = nhsRed,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    // Full-screen Dialog for PrescriptionStartRepeat
    if (showStartDialog) {
        Dialog(
            onDismissRequest = { showStartDialog = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Request repeat prescription",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                color = nhsBlack
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = nhsGrey5
                        )
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = nhsGrey5,
                        tonalElevation = 0.dp
                    ) {
                        Button(
                            onClick = { showStartDialog = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = nhsGreen
                            )
                        ) {
                            Text(
                                text = "Start now",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                },
                containerColor = nhsGrey5
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Body text
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Use this service to request a repeat prescription from your GP surgery.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = nhsBlack
                            )

                            TextButton(
                                onClick = { showAboutSheet = true },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Learn more",
                                    color = nhsBlue,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }

                    // Important information card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Important header with nhsYellow background
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(nhsYellow)
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Warning,
                                        contentDescription = null,
                                        tint = nhsBlack,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "Important",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = nhsBlack
                                    )
                                }

                                // Information text with nhsPaleYellow background
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(nhsPaleYellow)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Please allow 48 hours notice. Your GP may contact you to discuss your request.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsBlack,
                                        modifier = Modifier.padding(start = 36.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Urgent advice information text
                    item {
                        Text(
                            text = "For urgent medical advice, call 111 or visit 111.nhs.uk",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack
                        )
                    }
                }
            }
        }
    }

    // About prescriptions modal bottom sheet
    if (showAboutSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAboutSheet = false },
            sheetState = aboutSheetState,
            containerColor = nhsGrey5
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                item {
                    Text(
                        text = "About prescriptions",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = nhsBlack
                    )
                }

                // Image
                item {
                    Image(
                        painter = painterResource(
                            id = R.drawable.prescription_bag
                        ),
                        contentDescription = "Prescription bag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // What is a repeat prescription section
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "What is a repeat prescription?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = nhsBlack
                        )
                        Text(
                            text = "A repeat prescription is for medication you take regularly. Your GP will prescribe it for a set period, and you can request refills without needing a new appointment each time.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack
                        )
                    }
                }

                // How to request section
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "How to request",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = nhsBlack
                        )
                        Text(
                            text = "You can request repeat prescriptions through the NHS App, by phone, or in person at your GP surgery. Please allow at least 48 hours for your request to be processed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack
                        )
                    }
                }

                // Collection section
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Collecting your prescription",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = nhsBlack
                        )
                        Text(
                            text = "Once approved, collect your prescription from your nominated pharmacy. Some pharmacies offer home delivery services. Contact your pharmacy for more details.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack
                        )
                    }
                }

                // Emergency prescriptions section
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Emergency prescriptions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = nhsBlack
                        )
                        Text(
                            text = "If you've run out of medication and can't wait for a repeat prescription, you may be able to get an emergency supply from your pharmacy. Contact 111 for urgent medical advice.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack
                        )
                    }
                }

                // Close button
                item {
                    Button(
                        onClick = { showAboutSheet = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = nhsGreen
                        )
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryListItem(
    label: String,
    trailingButtonText: String,
    onTrailingButtonClick: () -> Unit,
    showDivider: Boolean,
    value: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = nhsBlack
                )
                if (value != null) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsGrey
                    )
                }
            }

            TextButton(
                onClick = onTrailingButtonClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = trailingButtonText,
                    color = nhsBlue,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = nhsGrey4,
                thickness = 1.dp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrescriptionFormRepeatPreview() {
    PrescriptionFormRepeat(rememberNavController(), modifier = Modifier)
}