package com.prototype.nhsappcv1.screens.yourhealth.prescriptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.R
import com.prototype.nhsappcv1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionStartRepeat(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
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
                    onClick = { navController.navigate("prescriptionFormRepeat") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
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

            // Body text with Learn more button
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
                        onClick = { showBottomSheet = true },
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
                        containerColor = nhsLightBlue
                    ),
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = nhsBlack,
                            modifier = Modifier.size(24.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Important",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = nhsBlack
                            )
                            Text(
                                text = "Please allow 48 hours notice. Your GP may contact you to discuss your request.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsBlack
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

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Modal Bottom Sheet for "About prescriptions"
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
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
                        onClick = { showBottomSheet = false },
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

@Preview(showSystemUi = true)
@Composable
fun PrescriptionStartRepeatPreview() {
    PrescriptionStartRepeat(rememberNavController(), modifier = Modifier)
}