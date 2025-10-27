package com.prototype.demonhsapp.screens.yourhealth.prescriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.PrescriptionCard
import com.prototype.demonhsapp.components.PrescriptionStatus
import com.prototype.demonhsapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Prescriptions2(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Active", "Past")

    // Calculate if we should show grey background based on scroll
    val showGreyBackground = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                    (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset > 100)
        }
    }

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
                        color = if (showGreyBackground.value) nhsBlack else Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (showGreyBackground.value) nhsBlack else Color.White
                        )
                    }
                },
                actions = {
                    // Order prescriptions button
                    FilledTonalButton(
                        onClick = { /* TODO: Order prescriptions */ },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (showGreyBackground.value) nhsBlue else Color.White,
                            contentColor = if (showGreyBackground.value) Color.White else nhsBlue
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Order")
                    }
                    Spacer(Modifier.width(8.dp))
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = if (showGreyBackground.value) nhsGrey5 else nhsBlue,
                    scrolledContainerColor = if (showGreyBackground.value) nhsGrey5 else nhsBlue
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
            // Blue header section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsBlue)
                ) {
                    // Description text
                    Text(
                        text = "Manage your repeat prescriptions and view your prescription history",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )

                    // Tabs
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = Color.White
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
                                        fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (selectedTabIndex == index) Color.White else Color.White.copy(alpha = 0.7f)
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
                                    medicationName = "Omeprazole",
                                    dosage = "20mg capsules - Take one daily before food",
                                    status = PrescriptionStatus.COLLECTED,
                                    prescribedDate = "20 October 2025",
                                    prescribedBy = "Dr. Emily White",
                                    quantity = "28 capsules",
                                    onClick = {
                                        navController.navigate(
                                            "prescriptionDetail/Omeprazole/" +
                                                    "20mg capsules - Take one daily before food/" +
                                                    "COLLECTED/" +
                                                    "20 October 2025/" +
                                                    "Dr. Emily White/" +
                                                    "28 capsules"
                                        )
                                    }
                                )

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