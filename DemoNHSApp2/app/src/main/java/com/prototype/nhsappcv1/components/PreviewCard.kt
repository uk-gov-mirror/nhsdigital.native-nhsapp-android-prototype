package com.prototype.nhsappcv1.components



import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Vaccines
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prototype.nhsappcv1.ui.theme.*
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PulsingCircle(
    color: Color = nhsYellow,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = modifier.size(8.dp)) {
        drawCircle(
            color = color,
            radius = size.minDimension / 2 * scale,
            alpha = alpha
        )
    }
}

@Composable
fun WidgetCard(
    headline: String,
    subheadline: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Headline
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                color = nhsBlack
            )

            // Subheadline with pulsing circle
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PulsingCircle(color = nhsYellow)

                Text(
                    text = subheadline,
                    style = MaterialTheme.typography.bodySmall,
                    color = nhsGrey,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Progress indicator with percentage
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = nhsBlue,
                    trackColor = nhsGrey4.copy(alpha = 0.3f),
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = nhsGrey
                )
            }
        }
    }
}

@Composable
fun AppointmentWidgetCard(
    headline: String,
    appointmentTime: String,
    appointmentDate: String,
    location: String = "",
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Calendar date box on the left
            Card(
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = nhsBlue
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Extract day number from date (e.g., "Tomorrow, 26 October" -> "26")
                    val dayNumber = appointmentDate.split(" ").getOrNull(1)?.replace(",", "") ?: ""

                    // Month (extract from date or show first 3 letters)
                    val month = appointmentDate.split(" ").lastOrNull()?.take(3)?.uppercase() ?: ""

                    Text(
                        text = month,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )

                    Text(
                        text = dayNumber,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
            }

            // Appointment details on the right
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Headline
                Text(
                    text = headline,
                    style = MaterialTheme.typography.labelMedium,
                    color = nhsGrey,
                    maxLines = 1
                )

                // Appointment time (large and prominent)
                Text(
                    text = appointmentTime,
                    style = MaterialTheme.typography.headlineMedium,
                    color = nhsBlack,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                // Location with icon (if provided)
                if (location.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.Place,
                            contentDescription = null,
                            tint = nhsGrey,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodySmall,
                            color = nhsGrey,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TestResultWidgetCard(
    headline: String,
    testName: String,
    result: String,
    resultDate: String,
    chartData: List<Float> = listOf(0.3f, 0.5f, 0.7f, 0.6f, 0.8f, 0.9f, 0.7f),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Headline
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                color = nhsBlack
            )

            // Test name
            Text(
                text = testName,
                style = MaterialTheme.typography.bodyMedium,
                color = nhsGrey.copy(alpha = 0.8f)
            )

            // Result value (large and prominent)
            Text(
                text = result,
                style = MaterialTheme.typography.headlineSmall,
                color = nhsBlack
            )

            Spacer(modifier = Modifier.weight(1f))

            // Simple line chart
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                val width = size.width
                val height = size.height
                val spacing = width / (chartData.size - 1)

                // Draw connecting lines
                for (i in 0 until chartData.size - 1) {
                    val startX = i * spacing
                    val startY = height - (chartData[i] * height)
                    val endX = (i + 1) * spacing
                    val endY = height - (chartData[i + 1] * height)

                    drawLine(
                        color = nhsBlue,
                        start = androidx.compose.ui.geometry.Offset(startX, startY),
                        end = androidx.compose.ui.geometry.Offset(endX, endY),
                        strokeWidth = 4f
                    )
                }

                // Draw points
                chartData.forEachIndexed { index, value ->
                    val x = index * spacing
                    val y = height - (value * height)

                    drawCircle(
                        color = nhsBlue,
                        radius = 6f,
                        center = androidx.compose.ui.geometry.Offset(x, y)
                    )
                }
            }

            // Result date
            Text(
                text = resultDate,
                style = MaterialTheme.typography.labelSmall,
                color = nhsGrey.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun PrescriptionWidgetCard(
    prescriptionType: String, // "One-off prescription" or "Repeat prescription"
    medicationName: String,
    status: String = "Ready to collect",
    progress: Float = 0.0f, // Progress value 0.0 to 1.0
    onClick: () -> Unit = {}, // Added onClick parameter
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick, // Made card clickable
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = nhsGreenTint
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Prescription type (small label)
                Text(
                    text = prescriptionType,
                    style = MaterialTheme.typography.labelMedium,
                    color = nhsGreen
                )

                // Medication name (large and prominent)
                Text(
                    text = medicationName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = nhsBlack,
                    modifier = Modifier.padding(end = 40.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Progress bar (without percentage label)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = nhsGreen,
                    trackColor = nhsGreen.copy(alpha = 0.2f),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Status with pulsing circle
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PulsingCircle(color = nhsGreen)

                    Text(
                        text = status,
                        style = MaterialTheme.typography.bodyMedium,
                        color = nhsGreen,
                        maxLines = 1
                    )
                }
            }

            // Icon at the top right
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Apps,
                contentDescription = null,
                tint = nhsGreen.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun VaccinationWidgetCard(
    headline: String = "Vaccinations",
    statusText: String, // "Up to date" or "Action needed"
    isUpToDate: Boolean,
    nextVaccination: String = "", // e.g., "Flu vaccine due in Nov"
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color.White
    val statusColor = nhsBlue

    Card(
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Headline
                Text(
                    text = headline,
                    style = MaterialTheme.typography.labelMedium,
                    color = statusColor
                )

                // Status (large and prominent)
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.headlineMedium,
                    color = nhsBlack,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(end = 40.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Next vaccination info or checkmark
                if (nextVaccination.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.Info,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = nextVaccination,
                            style = MaterialTheme.typography.bodySmall,
                            color = nhsGrey,
                            maxLines = 2
                        )
                    }
                } else if (isUpToDate) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "All vaccinations current",
                            style = MaterialTheme.typography.bodyMedium,
                            color = statusColor
                        )
                    }
                }
            }

            // Vaccination icon at the top right
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Outlined.Vaccines,
                contentDescription = null,
                tint = statusColor.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }
}


data class CardData(
    val headline: String,
    val subheadline: String,
    val progress: Float
)

@Composable
fun WidgetCardLazyRow(
    cards: List<CardData>,
    modifier: Modifier = Modifier,
    haptics: HapticFeedback? = null
) {
    val listState = rememberLazyListState()

    // Track scroll position and trigger haptics on card change
    LaunchedEffect(listState) {
        var previousFirstVisibleIndex = 0

        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { currentIndex ->
                if (currentIndex != previousFirstVisibleIndex) {
                    haptics?.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    previousFirstVisibleIndex = currentIndex
                }
            }
    }

    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cards) { card ->
            WidgetCard(
                headline = card.headline,
                subheadline = card.subheadline,
                progress = card.progress
            )
        }
    }
}

// Sample data for preview and usage
@Composable
fun getSampleCardData(): List<CardData> {
    return listOf(
        CardData(
            headline = "Daily Steps",
            subheadline = "7,500 of 10,000 steps",
            progress = 0.75f
        ),
        CardData(
            headline = "Water Intake",
            subheadline = "6 of 8 glasses today",
            progress = 0.75f
        ),
        CardData(
            headline = "Medications",
            subheadline = "2 of 3 taken today",
            progress = 0.67f
        ),
        CardData(
            headline = "Sleep Goal",
            subheadline = "7.5 of 8 hours",
            progress = 0.94f
        ),
        CardData(
            headline = "Exercise",
            subheadline = "30 min cardio completed",
            progress = 1.0f
        )
    )
}

// Previews for Android Studio

@Preview(showBackground = true, name = "Single Widget Card")
@Composable
fun WidgetCardPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            WidgetCard(
                headline = "Daily Goals",
                subheadline = "Keep up the great work!",
                progress = 0.65f
            )
        }
    }
}

@Preview(showBackground = true, name = "Widget Cards Lazy Row")
@Composable
fun WidgetCardLazyRowPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            WidgetCardLazyRow(
                cards = getSampleCardData()
            )
        }
    }
}

@Preview(showBackground = true, name = "Multiple Card States", heightDp = 800)
@Composable
fun AllCardStatesPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WidgetCard(
                    headline = "Low Progress",
                    subheadline = "Just getting started",
                    progress = 0.15f
                )

                WidgetCard(
                    headline = "Half Way",
                    subheadline = "You're doing great!",
                    progress = 0.50f
                )

                WidgetCard(
                    headline = "Almost Done",
                    subheadline = "Keep pushing!",
                    progress = 0.85f
                )

                WidgetCard(
                    headline = "Complete",
                    subheadline = "Goal achieved!",
                    progress = 1.0f
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Appointment Widget Card")
@Composable
fun AppointmentWidgetCardPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AppointmentWidgetCard(
                headline = "Next Appointment",
                appointmentTime = "10:30 AM",
                appointmentDate = "Tomorrow, 26 October",
                location = "St. Mary's Hospital, Cardiology"
            )
        }
    }
}

@Preview(showBackground = true, name = "Appointment Card No Location")
@Composable
fun AppointmentWidgetCardNoLocationPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AppointmentWidgetCard(
                headline = "Upcoming Appointment",
                appointmentTime = "2:00 PM",
                appointmentDate = "Monday, 28 October"
            )
        }
    }
}

@Preview(showBackground = true, name = "Test Result Widget Card")
@Composable
fun TestResultWidgetCardPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TestResultWidgetCard(
                headline = "Latest Test Result",
                testName = "Blood Glucose",
                result = "5.8 mmol/L",
                resultDate = "Tested on 24 October 2025",
                chartData = listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.65f, 0.8f, 0.7f)
            )
        }
    }
}

@Preview(showBackground = true, name = "Test Result Trend")
@Composable
fun TestResultTrendPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            TestResultWidgetCard(
                headline = "Blood Pressure",
                testName = "Systolic",
                result = "118 mmHg",
                resultDate = "Last 7 days",
                chartData = listOf(0.5f, 0.6f, 0.55f, 0.5f, 0.48f, 0.52f, 0.5f)
            )
        }
    }
}

@Preview(showBackground = true, name = "One-off Prescription Widget")
@Composable
fun PrescriptionWidgetCardOneOffPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PrescriptionWidgetCard(
                prescriptionType = "One-off prescription",
                medicationName = "Amoxicillin",
                status = "Ready to collect",
                progress = 0.85f
            )
        }
    }
}

@Preview(showBackground = true, name = "Repeat Prescription Widget")
@Composable
fun PrescriptionWidgetCardRepeatPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PrescriptionWidgetCard(
                prescriptionType = "Repeat prescription",
                medicationName = "Omeprazole",
                status = "Ready to collect",
                progress = 0.65f
            )
        }
    }
}

@Preview(showBackground = true, name = "Vaccination Up to Date")
@Composable
fun VaccinationWidgetCardUpToDatePreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            VaccinationWidgetCard(
                statusText = "Up to date",
                isUpToDate = true
            )
        }
    }
}

@Preview(showBackground = true, name = "Vaccination Action Needed")
@Composable
fun VaccinationWidgetCardActionNeededPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            VaccinationWidgetCard(
                statusText = "Action needed",
                isUpToDate = false,
                nextVaccination = "Flu vaccine due in November"
            )
        }
    }
}

@Preview(showBackground = true, name = "All Widget Cards", heightDp = 900)
@Composable
fun AllWidgetCardsPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WidgetCard(
                    headline = "Health Goals",
                    subheadline = "7,500 of 10,000 steps",
                    progress = 0.75f
                )

                AppointmentWidgetCard(
                    headline = "Next Appointment",
                    appointmentTime = "10:30 AM",
                    appointmentDate = "Tomorrow, 26 October",
                    location = "St. Mary's Hospital"
                )

                TestResultWidgetCard(
                    headline = "Latest Test Result",
                    testName = "Blood Glucose",
                    result = "5.8 mmol/L",
                    resultDate = "Tested 24 October"
                )

                PrescriptionWidgetCard(
                    prescriptionType = "One-off prescription",
                    medicationName = "Amoxicillin",
                    status = "Ready to collect",
                    progress = 0.85f
                )

                PrescriptionWidgetCard(
                    prescriptionType = "Repeat prescription",
                    medicationName = "Lisinopril",
                    status = "Processing",
                    progress = 0.45f
                )

                VaccinationWidgetCard(
                    statusText = "Up to date",
                    isUpToDate = true
                )
            }
        }
    }
}