package com.prototype.demonhsapp.components



import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prototype.demonhsapp.ui.theme.nhsDarkBlue
import com.prototype.demonhsapp.ui.theme.nhsYellow
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
            containerColor = nhsDarkBlue
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
                color = Color.White
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
                    color = Color.White,
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
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
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
            containerColor = nhsDarkBlue
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
            // Headline (e.g., "Upcoming Appointment")
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            // Appointment time (large and prominent)
            Text(
                text = appointmentTime,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            // Appointment date
            Text(
                text = appointmentDate,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Location (if provided)
            if (location.isNotEmpty()) {
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 2
                )
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
            containerColor = nhsDarkBlue
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
                color = Color.White
            )

            // Test name
            Text(
                text = testName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            // Result value (large and prominent)
            Text(
                text = result,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
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
                        color = Color.White,
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
                        color = Color.White,
                        radius = 6f,
                        center = androidx.compose.ui.geometry.Offset(x, y)
                    )
                }
            }

            // Result date
            Text(
                text = resultDate,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f)
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