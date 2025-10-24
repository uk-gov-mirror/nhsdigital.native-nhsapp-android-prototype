package com.prototype.demonhsapp.components



import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PulsingCircle(
    color: Color = MaterialTheme.colorScheme.primary,
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
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Headline
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Subheadline with pulsing circle
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PulsingCircle()

                Text(
                    text = subheadline,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }

            // Progress indicator with percentage
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
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
    modifier: Modifier = Modifier
) {
    LazyRow(
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