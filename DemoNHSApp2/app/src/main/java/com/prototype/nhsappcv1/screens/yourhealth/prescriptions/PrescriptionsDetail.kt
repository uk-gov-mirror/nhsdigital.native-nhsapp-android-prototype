package com.prototype.nhsappcv1.screens.yourhealth.prescriptions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.components.PrescriptionStatus
import com.prototype.nhsappcv1.ui.theme.*

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
    prescriptionType: String = "One-off prescription",
    pharmacyName: String = "Boots Pharmacy",
    pharmacyAddress: String = "123 High Street, London, SW1A 1AA",
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
                        color = nhsBlack
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = nhsBlack
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGreenTint,
                    scrolledContainerColor = nhsGreenTint
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
        containerColor = nhsGreenTint
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Green tint header section with status
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGreenTint)
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
                                    .background(prescriptionStatus.color)
                            )
                            Text(
                                text = prescriptionStatus.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = prescriptionStatus.color
                            )
                        }

                        // Prescription type
                        Text(
                            text = prescriptionType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Content section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGreenTint)
                        .padding(horizontal = 16.dp)
                ) {
                    // Details Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            // Thick green rectangle on the left
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .fillMaxHeight()
                                    .background(nhsGreen)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Prescription Details",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = nhsBlack
                                )

                                // Medication information
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
                                            text = "Medication",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = medicationName,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                                // Issued information (date and doctor)
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
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "Issued",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "$prescribedDate by $prescribedBy",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }

                                HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                                // Quantity and usage
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.Top
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
                                            text = "Quantity & Usage",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = quantity,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = dosage,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.Normal,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Pharmacy Information Card
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
                                text = "Pharmacy Information",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = nhsBlack
                            )

                            // Pharmacy name and address
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Outlined.LocalPharmacy,
                                    contentDescription = null,
                                    tint = nhsGrey2,
                                    modifier = Modifier.size(24.dp)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = pharmacyName,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = nhsBlack,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = pharmacyAddress,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey2,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 20.sp
                                    )
                                }
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
                                containerColor = nhsGreen
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
                        border = BorderStroke(1.dp, nhsBlue),
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

// New variant optimized for "Ready to collect" status
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailReady(
    navController: NavController,
    medicationName: String = "Amoxicillin",
    dosage: String = "500mg capsules - Take one three times daily",
    prescribedDate: String = "24 October 2025",
    prescribedBy: String = "Dr. Sarah Johnson",
    quantity: String = "21 capsules",
    prescriptionType: String = "One-off prescription",
    pharmacyName: String = "Boots Pharmacy",
    pharmacyAddress: String = "123 High Street, London, SW1A 1AA",
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Generate barcode number once
    val barcodeNumber = remember {
        (100000000000L..999999999999L).random().toString()
    }

    // State for showing the full-screen barcode modal
    var showBarcodeModal by remember { mutableStateOf(false) }

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
                        color = nhsBlack
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = nhsBlack
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGreenTint,
                    scrolledContainerColor = nhsGreenTint
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = nhsGreenTint
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Green tint header section with status
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGreenTint)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 24.dp)
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
                                    .background(PrescriptionStatus.READY.color)
                            )
                            Text(
                                text = PrescriptionStatus.READY.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrescriptionStatus.READY.color
                            )
                        }

                        // Prescription type
                        Text(
                            text = prescriptionType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsBlack.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Content section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGreenTint)
                        .padding(horizontal = 16.dp)
                ) {
                    // Barcode Card - Clickable
                    Card(
                        onClick = { showBarcodeModal = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Show this barcode at the Pharmacy to collect your prescription",
                                style = MaterialTheme.typography.labelMedium,
                                color = nhsGrey2,
                                modifier = Modifier.padding(bottom = 12.dp),
                                textAlign = TextAlign.Center
                            )
                            // Barcode representation (simplified)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(60.dp)
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
                            // Barcode number
                            Text(
                                text = barcodeNumber.chunked(3).joinToString(" "),
                                style = MaterialTheme.typography.labelLarge,
                                color = nhsBlack,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                modifier = Modifier.padding(top = 8.dp),
                                letterSpacing = 1.5.sp
                            )

                            // Tap to enlarge hint
                            Text(
                                text = "Tap to enlarge",
                                style = MaterialTheme.typography.bodySmall,
                                color = nhsBlue,
                                modifier = Modifier.padding(top = 8.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Pharmacy Information Card
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
                                text = "Collect From",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = nhsBlack
                            )

                            // Pharmacy name and address
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Outlined.LocalPharmacy,
                                    contentDescription = null,
                                    tint = nhsGrey2,
                                    modifier = Modifier.size(24.dp)
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = pharmacyName,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = nhsBlack,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = pharmacyAddress,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey2,
                                        fontWeight = FontWeight.Normal,
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                            HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                            // Find pharmacy button
                            Button(
                                onClick = { /* TODO: Navigate to pharmacy */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = nhsGreen
                                ),
                                shape = RoundedCornerShape(12.dp)
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
                    }

                    // Prescription Details Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                        ) {
                            // Thick green rectangle on the left
                            Box(
                                modifier = Modifier
                                    .width(12.dp)
                                    .fillMaxHeight()
                                    .background(nhsGreen)
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Prescription Details",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = nhsBlack
                                )

                                // Medication information
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
                                            text = "Medication",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = medicationName,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                                // Issued information (date and doctor)
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
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "Issued",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "$prescribedDate by $prescribedBy",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }

                                HorizontalDivider(color = nhsGrey4.copy(alpha = 0.3f))

                                // Quantity and usage
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.Top
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
                                            text = "Quantity & Usage",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = nhsGrey2,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = quantity,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = dosage,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = nhsBlack,
                                            fontWeight = FontWeight.Normal,
                                            lineHeight = 20.sp
                                        )
                                    }
                                }
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

                    // Contact GP button
                    OutlinedButton(
                        onClick = { /* TODO: Contact GP */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = nhsBlue
                        ),
                        border = BorderStroke(1.dp, nhsBlue),
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

        // Full-screen barcode modal
        if (showBarcodeModal) {
            FullScreenBarcodeModal(
                barcodeNumber = barcodeNumber,
                medicationName = medicationName,
                onDismiss = { showBarcodeModal = false }
            )
        }
    }
}

// Full-screen barcode modal with rotated content
@Composable
fun FullScreenBarcodeModal(
    barcodeNumber: String,
    medicationName: String,
    onDismiss: () -> Unit
) {
    // Full-screen dialog
    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .systemBarsPadding()
        ) {
            // Close button (not rotated)
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(nhsGrey5)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = nhsBlack
                )
            }

            // Center content - rotated 90 degrees for landscape viewing
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp)
                    .graphicsLayer {
                        rotationZ = 90f
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pharmacy icon
                Icon(
                    Icons.Outlined.LocalPharmacy,
                    contentDescription = null,
                    tint = nhsGreen,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(bottom = 16.dp)
                )

                // Title
                Text(
                    text = "Show at Pharmacy",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = nhsBlack,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Medication name
                Text(
                    text = medicationName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = nhsGreen,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Large barcode - vertical stripes
                Box(
                    modifier = Modifier
                        .width(400.dp)
                        .height(150.dp)
                        .background(Color.White)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Generate barcode-like pattern
                        val pattern = listOf(3, 1, 2, 1, 3, 2, 1, 3, 1, 2, 3, 1, 2, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 3)
                        pattern.forEach { width ->
                            Box(
                                modifier = Modifier
                                    .width((width * 4).dp)
                                    .fillMaxHeight()
                                    .background(nhsBlack)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                        }
                    }
                }

                // Large barcode number
                Text(
                    text = barcodeNumber.chunked(3).joinToString("  "),
                    style = MaterialTheme.typography.headlineMedium,
                    color = nhsBlack,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    modifier = Modifier.padding(top = 24.dp),
                    letterSpacing = 4.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions
                Text(
                    text = "Present this barcode to collect your prescription",
                    style = MaterialTheme.typography.titleMedium,
                    color = nhsGrey2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
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
        status = "PENDING",
        prescribedDate = "24 October 2025",
        prescribedBy = "Dr. Sarah Johnson",
        quantity = "21 capsules",
        prescriptionType = "One-off prescription",
        pharmacyName = "Boots Pharmacy",
        pharmacyAddress = "123 High Street, London, SW1A 1AA"
    )
}

@Preview(showSystemUi = true)
@Composable
fun PrescriptionDetailReadyPreview() {
    PrescriptionDetailReady(
        navController = rememberNavController(),
        medicationName = "Amoxicillin",
        dosage = "500mg capsules - Take one three times daily",
        prescribedDate = "24 October 2025",
        prescribedBy = "Dr. Sarah Johnson",
        quantity = "21 capsules",
        prescriptionType = "One-off prescription",
        pharmacyName = "Boots Pharmacy",
        pharmacyAddress = "123 High Street, London, SW1A 1AA"
    )
}

@Preview(showSystemUi = true, widthDp = 800, heightDp = 400)
@Composable
fun FullScreenBarcodeModalPreview() {
    FullScreenBarcodeModal(
        barcodeNumber = "123456789012",
        medicationName = "Amoxicillin",
        onDismiss = {}
    )
}