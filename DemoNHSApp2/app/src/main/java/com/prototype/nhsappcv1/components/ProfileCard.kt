package com.prototype.nhsappcv1.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappcv1.R
import com.prototype.nhsappcv1.ui.theme.*

@Composable
fun ProfileCard(
    name: String,
    nhsNumber: String,
    dateOfBirth: String = "15 March 1985",
    address: String = "123 High Street\nLondon, SW1A 1AA",
    gpPractice: String = "Central Medical Centre",
    bloodType: String = "O+",
    isFlipped: Boolean = false,
    onFlip: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "cardRotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onFlip()
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Flip the content when showing the back
                    if (rotation > 90f) {
                        rotationY = 180f
                    }
                }
        ) {
            if (rotation <= 90f) {
                // Front side
                ProfileCardFront(
                    name = name,
                    nhsNumber = nhsNumber,
                    dateOfBirth = dateOfBirth
                )
            } else {
                // Back side
                ProfileCardBack(
                    address = address,
                    gpPractice = gpPractice,
                    bloodType = bloodType
                )
            }
        }
    }
}

@Composable
private fun ProfileCardFront(
    name: String,
    nhsNumber: String,
    dateOfBirth: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Blue accent strip at top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(nhsBlue)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section: NHS Logo with Profile text
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nhs_logo),
                        contentDescription = "NHS Logo",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = nhsBlack
                    )
                }

                // Profile photo placeholder
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(nhsLightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = nhsBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Bottom section: Profile information
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Name
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = nhsBlack,
                    fontSize = 22.sp
                )

                // Date of Birth
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Date of Birth:",
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsGrey,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = dateOfBirth,
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsBlack,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // NHS Number
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "NHS Number:",
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsGrey,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = nhsNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsBlack,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileCardBack(
    address: String,
    gpPractice: String,
    bloodType: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Blue accent strip at top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(nhsBlue)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section: Title and Blood Type Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Additional Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = nhsBlack
                )

                // Blood Type Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = nhsBlue,
                    modifier = Modifier.size(width = 56.dp, height = 56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = bloodType,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 24.sp
                            )
                            Text(
                                text = "BLOOD",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Middle section: Details
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Address
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Address",
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsGrey,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsBlack,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 18.sp
                    )
                }

                // GP Practice
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Registered GP Practice",
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsGrey,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = gpPractice,
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsBlack,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Bottom section: Tap to flip hint
            Text(
                text = "Tap card to flip back",
                style = MaterialTheme.typography.labelSmall,
                color = nhsGrey2,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ProfileCardFrontPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ProfileCard(
            name = "John Smith",
            nhsNumber = "123 456 7890",
            dateOfBirth = "15 March 1985",
            address = "123 High Street\nLondon, SW1A 1AA",
            gpPractice = "Central Medical Centre",
            bloodType = "O+",
            isFlipped = false
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ProfileCardBackPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ProfileCard(
            name = "John Smith",
            nhsNumber = "123 456 7890",
            dateOfBirth = "15 March 1985",
            address = "123 High Street\nLondon, SW1A 1AA",
            gpPractice = "Central Medical Centre",
            bloodType = "O+",
            isFlipped = true
        )
    }
}