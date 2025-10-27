package com.prototype.demonhsapp.screens.yourhealth.prescriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.PrescriptionStatus
import com.prototype.demonhsapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetail(
    navController: NavController,
    medicationName: String = "Amoxicillin",
    dosage: String = "500mg capsules - Take one three times daily",
    status: String = "READY",
    prescribedDate: String = "24 October 2025",
    prescribedBy: String = "Dr. Sarah Johnson",
    quantity: String = "21 capsules",
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Parse status
    val prescriptionStatus = when (status) {
        "READY" -> PrescriptionStatus.READY
        "PENDING" -> PrescriptionStatus.PENDING
        "COLLECTED" -> PrescriptionStatus.COLLECTED
        "EXPIRED" -> PrescriptionStatus.EXPIRED
        else -> PrescriptionStatus.PENDING
    }

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
                        medicationName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium,
                        color = if (showGreyBackground.value) nhsBlack else Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = if (showGreyBackground.value) nhsBlack else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = if (showGreyBackground.value) nhsGrey5 else prescriptionStatus.color,
                    scrolledContainerColor = if (showGreyBackground.value) nhsGrey5 else prescriptionStatus.color
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.height(140.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Prescription Reference",
                        style = MaterialTheme.typography.labelSmall,
                        color = nhsGrey2,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Barcode representation (simplified)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(50.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        // Simplified barcode pattern using vertical stripes
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Generate barcode-like pattern
                            val pattern = listOf(3, 1, 2, 1, 3, 2, 1, 3, 1, 2, 3, 1, 2, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 3)
                            pattern.forEach { width ->
                                Box(
                                    modifier = Modifier
                                        .width((width * 3).dp)
                                        .fillMaxHeight()
                                        .background(nhsBlack)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                            }
                        }
                    }
                    // Barcode number - generate random 12-digit number
                    val barcodeNumber = remember {
                        (100000000000L..999999999999L).random().toString()
                    }
                    Text(
                        text = barcodeNumber.chunked(3).joinToString(" "),
                        style = MaterialTheme.typography.labelLarge,
                        color = nhsBlack,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        modifier = Modifier.padding(top = 4.dp),
                        letterSpacing = 1.5.sp
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
        ) {
            // Colored header section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(prescriptionStatus.color)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 32.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Status badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                            Text(
                                text = prescriptionStatus.displayName,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Grey section with details
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    // Prescription Information Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Prescription Information",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = nhsBlack
                            )

                            // Dosage information
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Medication,
                                    contentDescription = null,
                                    tint = nhsGrey2,
                                    modifier = Modifier.size(24.dp)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Dosage",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = nhsGrey2,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = dosage,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = nhsBlack,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                            // Quantity information
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Inventory2,
                                    contentDescription = null,
                                    tint = nhsGrey2,
                                    modifier = Modifier.size(24.dp)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Quantity",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = nhsGrey2,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = quantity,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = nhsBlack,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                            // Single sentence for issued information
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = nhsGrey2,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Issued on $prescribedDate by $prescribedBy",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = nhsBlack,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Instructions Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Important Information",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = nhsBlack
                            )

                            Text(
                                text = "• Take this medication exactly as prescribed by your doctor",
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsBlack,
                                lineHeight = 20.sp
                            )

                            Text(
                                text = "• Complete the full course even if you feel better",
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsBlack,
                                lineHeight = 20.sp
                            )

                            Text(
                                text = "• Store in a cool, dry place away from direct sunlight",
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsBlack,
                                lineHeight = 20.sp
                            )

                            Text(
                                text = "• Keep out of reach of children",
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsBlack,
                                lineHeight = 20.sp
                            )
                        }
                    }

                    // Action buttons
                    if (prescriptionStatus == PrescriptionStatus.READY) {
                        Button(
                            onClick = { /* TODO: Navigate to pharmacy */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(bottom = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = nhsBlue
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Place,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Find my pharmacy",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { /* TODO: Contact GP */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = nhsBlue
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            Icons.Outlined.ContactPage,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Contact GP surgery",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrescriptionDetailPreview() {
    PrescriptionDetail(
        navController = rememberNavController(),
        medicationName = "Amoxicillin",
        dosage = "500mg capsules - Take one three times daily",
        status = "READY",
        prescribedDate = "24 October 2025",
        prescribedBy = "Dr. Sarah Johnson",
        quantity = "21 capsules"
    )
}