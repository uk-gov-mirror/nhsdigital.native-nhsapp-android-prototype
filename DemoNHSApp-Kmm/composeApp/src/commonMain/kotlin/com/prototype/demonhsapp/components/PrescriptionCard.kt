package com.prototype.demonhsapp.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGreen
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
            // Header with NHS green accent
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Medication icon in grey circle
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(nhsGrey5),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Medication,
                            contentDescription = null,
                            tint = nhsGrey,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = prescriptionType,
                            style = MaterialTheme.typography.labelMedium,
                            color = nhsGrey,
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
                }
            }

            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
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
                color = nhsGrey2,
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
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier.size(12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing circle
        Box(
            modifier = Modifier
                .size(12.dp * scale)
                .alpha(alpha * 0.4f)
                .clip(CircleShape)
                .background(color)
        )

        // Inner solid circle
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