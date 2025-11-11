package com.prototype.nhsappcv1.screens.yourhealth.prescriptions

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.components.PrescriptionCard
import com.prototype.nhsappcv1.components.PrescriptionStatus
import com.prototype.nhsappcv1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Prescriptions2(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Active", "Past")

    // Tooltip state for Request button
    val tooltipState = rememberTooltipState(isPersistent = true)
    var showTooltip by remember { mutableStateOf(true) }

    // Request menu state
    var showRequestMenu by remember { mutableStateOf(false) }

    // Options menu state
    var showOptionsMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Prescriptions",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        color = nhsBlack
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = nhsBlack
                        )
                    }
                },
                actions = {
                    // Button group for prescription actions
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Request prescriptions button with tooltip and menu
                        Box {
                            if (showTooltip) {
                                TooltipBox(
                                    positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                                    tooltip = {
                                        RichTooltip(
                                            colors = TooltipDefaults.richTooltipColors(
                                                containerColor = nhsBlue,
                                                contentColor = MaterialTheme.colorScheme.onPrimary
                                            ),
                                            action = {
                                                TextButton(
                                                    onClick = { showTooltip = false }
                                                ) {
                                                    Text(
                                                        "Dismiss",
                                                        color = MaterialTheme.colorScheme.onPrimary
                                                    )
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = "You can request your repeat or emergency prescriptions",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.width(200.dp)
                                            )
                                        }
                                    },
                                    state = tooltipState
                                ) {
                                    IconButton(
                                        onClick = { showRequestMenu = true }
                                    ) {
                                        Icon(
                                            Icons.Outlined.Add,
                                            contentDescription = "Request prescriptions",
                                            tint = nhsBlue,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }

                                LaunchedEffect(Unit) {
                                    kotlinx.coroutines.delay(3000) // 3 seconds delay
                                    tooltipState.show()
                                }
                            } else {
                                IconButton(
                                    onClick = { showRequestMenu = true }
                                ) {
                                    Icon(
                                        Icons.Outlined.Add,
                                        contentDescription = "Request prescriptions",
                                        tint = nhsBlue,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                            // Request prescription menu
                            DropdownMenu(
                                expanded = showRequestMenu,
                                onDismissRequest = { showRequestMenu = false },
                                modifier = Modifier.background(nhsGrey5)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Repeat prescription") },
                                    onClick = {
                                        showRequestMenu = false
                                        navController.navigate("prescriptionFormRepeat")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Add,
                                            contentDescription = null,
                                            tint = nhsBlue
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Emergency prescription") },
                                    onClick = {
                                        showRequestMenu = false
                                        // TODO: Navigate to emergency prescription request
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.Add,
                                            contentDescription = null,
                                            tint = nhsBlue
                                        )
                                    }
                                )
                            }
                        }

                        // Options menu button (Pharmacy & Medicine records)
                        Box {
                            IconButton(
                                onClick = { showOptionsMenu = true }
                            ) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "More options",
                                    tint = nhsBlue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = showOptionsMenu,
                                onDismissRequest = { showOptionsMenu = false },
                                modifier = Modifier.background(nhsGrey5)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Chosen Pharmacy") },
                                    onClick = {
                                        showOptionsMenu = false
                                        // TODO: Navigate to chosen pharmacy
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.LocationOn,
                                            contentDescription = null,
                                            tint = nhsBlue
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Medicine record") },
                                    onClick = {
                                        showOptionsMenu = false
                                        // TODO: Navigate to medicine record
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Outlined.List,
                                            contentDescription = null,
                                            tint = nhsBlue
                                        )
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey5
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = nhsGrey5
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                ) {
                    // Tabs
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = nhsGrey5,
                        contentColor = nhsBlue,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = nhsBlue,
                                height = 3.dp
                            )
                        },
                        divider = {
                            HorizontalDivider(
                                color = nhsGrey4,
                                thickness = 1.dp
                            )
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTabIndex == index) nhsBlue else nhsGrey
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Grey section with prescription cards
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    when (selectedTabIndex) {
                        0 -> {
                            // Active prescriptions
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PrescriptionCard(
                                    medicationName = "Omeprazole",
                                    dosage = "20mg capsules - Take one daily before food",
                                    status = PrescriptionStatus.READY,
                                    prescribedDate = "20 October 2025",
                                    prescribedBy = "Dr. Emily White",
                                    quantity = "28 capsules",
                                    prescriptionType = "Repeat prescription",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Omeprazole/" +
                                                    "20mg capsules - Take one daily before food/" +
                                                    "READY/" +
                                                    "20 October 2025/" +
                                                    "Dr. Emily White/" +
                                                    "28 capsules"
                                        )
                                    }
                                )

                                PrescriptionCard(
                                    medicationName = "Amoxicillin",
                                    dosage = "500mg capsules - Take one three times daily",
                                    status = PrescriptionStatus.READY,
                                    prescribedDate = "24 October 2025",
                                    prescribedBy = "Dr. Sarah Johnson",
                                    quantity = "21 capsules",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Amoxicillin/" +
                                                    "500mg capsules - Take one three times daily/" +
                                                    "READY/" +
                                                    "24 October 2025/" +
                                                    "Dr. Sarah Johnson/" +
                                                    "21 capsules"
                                        )
                                    }
                                )

                                PrescriptionCard(
                                    medicationName = "Paracetamol",
                                    dosage = "500mg tablets - Take one or two every 4-6 hours",
                                    status = PrescriptionStatus.PENDING,
                                    prescribedDate = "25 October 2025",
                                    prescribedBy = "Dr. Michael Brown",
                                    quantity = "32 tablets",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Paracetamol/" +
                                                    "500mg tablets - Take one or two every 4-6 hours/" +
                                                    "PENDING/" +
                                                    "25 October 2025/" +
                                                    "Dr. Michael Brown/" +
                                                    "32 tablets"
                                        )
                                    }
                                )

                                PrescriptionCard(
                                    medicationName = "Lisinopril",
                                    dosage = "10mg tablets - Take one daily",
                                    status = PrescriptionStatus.READY,
                                    prescribedDate = "23 October 2025",
                                    prescribedBy = "Dr. Emily White",
                                    quantity = "28 tablets",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Lisinopril/" +
                                                    "10mg tablets - Take one daily/" +
                                                    "READY/" +
                                                    "23 October 2025/" +
                                                    "Dr. Emily White/" +
                                                    "28 tablets"
                                        )
                                    }
                                )
                            }
                        }
                        1 -> {
                            // Past prescriptions (collected)
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                PrescriptionCard(
                                    medicationName = "Ibuprofen",
                                    dosage = "400mg tablets - Take one when required",
                                    status = PrescriptionStatus.COLLECTED,
                                    prescribedDate = "18 October 2025",
                                    prescribedBy = "Dr. Sarah Johnson",
                                    quantity = "24 tablets",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Ibuprofen/" +
                                                    "400mg tablets - Take one when required/" +
                                                    "COLLECTED/" +
                                                    "18 October 2025/" +
                                                    "Dr. Sarah Johnson/" +
                                                    "24 tablets"
                                        )
                                    }
                                )

                                PrescriptionCard(
                                    medicationName = "Metformin",
                                    dosage = "500mg tablets - Take two twice daily with meals",
                                    status = PrescriptionStatus.COLLECTED,
                                    prescribedDate = "15 October 2025",
                                    prescribedBy = "Dr. Michael Brown",
                                    quantity = "112 tablets",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Metformin/" +
                                                    "500mg tablets - Take two twice daily with meals/" +
                                                    "COLLECTED/" +
                                                    "15 October 2025/" +
                                                    "Dr. Michael Brown/" +
                                                    "112 tablets"
                                        )
                                    }
                                )

                                PrescriptionCard(
                                    medicationName = "Atorvastatin",
                                    dosage = "20mg tablets - Take one at bedtime",
                                    status = PrescriptionStatus.COLLECTED,
                                    prescribedDate = "10 October 2025",
                                    prescribedBy = "Dr. Sarah Johnson",
                                    quantity = "28 tablets",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Atorvastatin/" +
                                                    "20mg tablets - Take one at bedtime/" +
                                                    "COLLECTED/" +
                                                    "10 October 2025/" +
                                                    "Dr. Sarah Johnson/" +
                                                    "28 tablets"
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Prescriptions2Preview() {
    Prescriptions2(rememberNavController(), modifier = Modifier)
}