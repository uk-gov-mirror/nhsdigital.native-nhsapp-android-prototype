package com.prototype.nhsappcv1.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappcv1.ui.theme.*

enum class PrescriptionStatus(val displayName: String, val color: Color) {
    PENDING("Pending", nhsBlue),
    READY("Ready to collect", nhsGreen),
    COLLECTED("Collected", nhsGrey),
    EXPIRED("Expired", Color(0xFFEF5350))
}

@Composable
fun PrescriptionCard(
    medicationName: String,
    dosage: String,
    status: PrescriptionStatus,
    prescribedDate: String,
    prescribedBy: String,
    quantity: String = "",
    prescriptionType: String = "One-off prescription", // "Repeat prescription" or "One-off prescription"
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Track pressed state for animation
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animate scale when pressed
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Remove ripple to only show scale
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = nhsGreenTint
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header without icon on left, with Apps icon on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = prescriptionType,
                        style = MaterialTheme.typography.labelMedium,
                        color = nhsBlack.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    )
                    Text(
                        text = medicationName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = nhsBlack,
                        fontSize = 18.sp
                    )
                }

                // Apps icon on the right
                Icon(
                    imageVector = Icons.Outlined.Apps,
                    contentDescription = null,
                    tint = nhsGrey.copy(alpha = 0.4f),
                    modifier = Modifier.size(24.dp)
                )
            }

            HorizontalDivider(
                color = nhsGrey2.copy(alpha = 0.5f),
                thickness = 1.dp
            )

            // Status with icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusIndicator(status = status)
                    Text(
                        text = status.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = status.color,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Prescription details as single sentence
            Text(
                text = "Issued on $prescribedDate by $prescribedBy",
                style = MaterialTheme.typography.bodySmall,
                color = nhsBlack.copy(alpha = 0.7f),
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun StatusIndicator(status: PrescriptionStatus) {
    when (status) {
        PrescriptionStatus.PENDING -> {
            // Static hourglass for pending
            Icon(
                imageVector = Icons.Outlined.HourglassEmpty,
                contentDescription = null,
                tint = status.color,
                modifier = Modifier.size(16.dp)
            )
        }
        PrescriptionStatus.COLLECTED -> {
            // Static checkmark for collected
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = status.color,
                modifier = Modifier.size(16.dp)
            )
        }
        else -> {
            // Pulsing dot for other statuses (READY, EXPIRED)
            PulsingStatusIndicator(color = status.color)
        }
    }
}

@Composable
private fun PulsingStatusIndicator(color: Color) {
    Box(
        modifier = Modifier.size(12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Simple solid circle - no animation
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun PrescriptionCardPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrescriptionCard(
            medicationName = "Amoxicillin",
            dosage = "500mg capsules - Take one three times daily",
            status = PrescriptionStatus.READY,
            prescribedDate = "24 October 2025",
            prescribedBy = "Dr. Sarah Johnson",
            quantity = "21 capsules",
            prescriptionType = "One-off prescription"
        )

        PrescriptionCard(
            medicationName = "Paracetamol",
            dosage = "500mg tablets - Take one or two every 4-6 hours",
            status = PrescriptionStatus.PENDING,
            prescribedDate = "25 October 2025",
            prescribedBy = "Dr. Michael Brown",
            quantity = "32 tablets",
            prescriptionType = "Repeat prescription"
        )

        PrescriptionCard(
            medicationName = "Omeprazole",
            dosage = "20mg capsules - Take one daily before food",
            status = PrescriptionStatus.COLLECTED,
            prescribedDate = "20 October 2025",
            prescribedBy = "Dr. Emily White",
            quantity = "28 capsules",
            prescriptionType = "Repeat prescription"
        )
    }
}