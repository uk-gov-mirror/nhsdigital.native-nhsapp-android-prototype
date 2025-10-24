package com.prototype.demonhsapp.components



import android.annotation.SuppressLint
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

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WidgetCard(
    headline: String,
    subheadline: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val cardWidth = maxWidth * 0.5f

        Card(
            modifier = Modifier
                .width(cardWidth)
                .padding(16.dp),
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
}

data class CardData(
    val headline: String,
    val subheadline: String,
    val progress: Float
)

@Composable
fun MultipleCardsCPreview() {
    val cards = listOf(
        CardData("Water Intake", "6 of 8 glasses today", 0.75f),
        CardData("Project Progress", "23 of 50 tasks completed", 0.46f),
        CardData("Daily Steps", "8,240 of 10,000 steps", 0.82f),
        CardData("Reading Goal", "3 of 5 books this month", 0.60f)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
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

@Preview(showBackground = true, name = "Widget Cards Row", widthDp = 360)
@Composable
fun MultipleCardsRowPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            MultipleCardsCPreview()
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