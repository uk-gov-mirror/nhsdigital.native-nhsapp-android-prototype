package com.prototype.nhsappcv1.screens.yourhealth.prescriptions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionFormRepeat(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Discard confirmation dialog state
    var showDiscardDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Repeat prescription",
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
                tonalElevation = 0.dp,
                modifier = Modifier.height(100.dp)
            ) {
                Button(
                    onClick = { /* TODO: Submit prescription request */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
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

            // Grey header
            item {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = nhsGrey
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
                            icon = Icons.Outlined.LocationOn,
                            emptyStateText = "No pharmacy selected",
                            trailingButtonText = "Add",
                            onTrailingButtonClick = { /* TODO: Add pharmacy */ },
                            showDivider = true
                        )

                        // Medicines requested
                        SummaryListItem(
                            label = "Medicines requested",
                            icon = Icons.Outlined.Medication,
                            emptyStateText = "No medicines added",
                            trailingButtonText = "Add",
                            onTrailingButtonClick = { /* TODO: Add medicines */ },
                            showDivider = true
                        )

                        // Additional information
                        SummaryListItem(
                            label = "Additional information",
                            icon = Icons.Outlined.Info,
                            emptyStateText = "None",
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
}

@Composable
fun SummaryListItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    emptyStateText: String,
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
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = nhsBlue,
                    modifier = Modifier.size(24.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = nhsBlack
                    )
                    Text(
                        text = value ?: emptyStateText,
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