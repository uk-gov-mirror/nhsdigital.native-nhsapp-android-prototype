package com.prototype.nhsappdv1.screens.home

import android.view.SoundEffectConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Vaccines
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.R
import com.prototype.nhsappdv1.components.AppointmentWidgetCard
import com.prototype.nhsappdv1.components.MenuWidgetCard
import com.prototype.nhsappdv1.components.TestResultWidgetCard
import com.prototype.nhsappdv1.components.WidgetCard
import com.prototype.nhsappdv1.navigation.Routes
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsDarkBlue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier,
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "Summary",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                        Text(
                            LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.nhs_logo_2),
                        contentDescription = "NHS Logo",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(48.dp),
                        contentScale = ContentScale.Fit
                    )
                },
                actions = {

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsBlue,
                    scrolledContainerColor = nhsBlue,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Full screen Blue Gradient Section with vertical pager
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(nhsBlue, nhsDarkBlue)
                        )
                    )
            ) {
                VerticalPagerCards(
                    navController = navController,
                    view = view,
                    haptics = haptics
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalPagerCards(
    navController: NavController,
    view: android.view.View,
    haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // All widget items
    val widgetItems = remember {
        listOf(
            WidgetItem.PrescriptionWidget,
            WidgetItem.AppointmentWidget,
            WidgetItem.TestResultWidget,
            WidgetItem.HealthGoalsWidget,
            WidgetItem.PrescriptionService(navController, view, haptics),
            WidgetItem.AppointmentService(navController, view, haptics),
            WidgetItem.TestResultService(view, haptics),
            WidgetItem.VaccinationService(view, haptics),
            WidgetItem.DocumentService(view, haptics),
            WidgetItem.NHS111Info(context, view, haptics),
            WidgetItem.HealthAtoZInfo(context, view, haptics),
            WidgetItem.NHSServicesInfo(context, view, haptics)
        )
    }

    val pagerState = rememberPagerState(pageCount = { widgetItems.size })

    // Track page changes for haptic feedback
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage >= 0) {
            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // Invisible pager for swipe detection
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .matchParentSize()
                .zIndex(0f), // sits behind
            userScrollEnabled = true
        ) { page ->
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(pagerState) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dragAmount ->
                            scope.launch {
                                // pager scroll is inverted
                                // dragAmount > 0 means dragging DOWN
                                pagerState.scrollBy(-dragAmount * 5f)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                // snap to nearest page
                                val offset = pagerState.currentPageOffsetFraction
                                val target = when {
                                    offset > 0.5f -> pagerState.currentPage + 1
                                    offset < -0.5f -> pagerState.currentPage - 1
                                    else -> pagerState.currentPage
                                }.coerceIn(0, widgetItems.lastIndex)
                                pagerState.animateScrollToPage(target)
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            // Render all cards in the same Box space to create stacking effect
            // Render background cards first, then current card last (on top)

            // First render all non-current cards
            widgetItems.indices.forEach { index ->
                if (index != pagerState.currentPage) {
                    val item = widgetItems[index]
                    val pageOffset = (pagerState.currentPage - index).toFloat() + pagerState.currentPageOffsetFraction

                    // Only render visible cards (previous, next)
                    if (pageOffset.absoluteValue <= 2f) {
                        StackedCardInPager(
                            item = item,
                            pageOffset = pageOffset,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(0.65f)
                        )
                    }
                }
            }

            // Then render current card last so it's on top
            val currentItem = widgetItems[pagerState.currentPage]
            StackedCardInPager(
                item = currentItem,
                pageOffset = pagerState.currentPageOffsetFraction,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.65f)
            )
        }
    }
}

@Composable
fun StackedCardInPager(
    item: WidgetItem,
    pageOffset: Float,
    modifier: Modifier = Modifier
) {
    // Calculate animations based on page position and transition direction
    // pageOffset > 0 means this page is BEFORE current (swipe down shows it)
    // pageOffset < 0 means this page is AFTER current (swipe up shows it)

    val translationY = when {
        // Card transitioning out - being swiped away
        pageOffset > 0 && pageOffset <= 1 -> {
            // Previous page coming into view (swipe down gesture)
            // Current card goes UP and behind
            -200f * pageOffset
        }
        pageOffset < 0 && pageOffset >= -1 -> {
            // Next page coming into view (swipe up gesture)
            // Current card goes DOWN and behind
            -200f * pageOffset // This will be positive (going down)
        }
        // Cards in stack position
        pageOffset > 1 -> -80f // Previous cards stacked above
        pageOffset < -1 -> 80f // Next cards stacked below
        else -> 0f // Current card at center
    }

    // Scale animation - current card is ALWAYS largest
    val scale = when {
        // Current card - always full size
        pageOffset == 0f -> 1f

        // Card transitioning out (being swiped away)
        pageOffset > 0 && pageOffset <= 1 -> {
            1f - (pageOffset * 0.15f) // Shrinks from 1.0 to 0.85
        }
        pageOffset < 0 && pageOffset >= -1 -> {
            1f - ((-pageOffset) * 0.15f) // Shrinks from 1.0 to 0.85
        }

        // Cards in stack - smaller
        else -> 0.85f
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                this.translationY = translationY
                this.scaleX = scale
                this.scaleY = scale
            }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (item) {
            is WidgetItem.PrescriptionWidget -> {
                WidgetCard(
                    headline = "Prescriptions",
                    subheadline = "2 ready to collect",
                    progress = 0.67f,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.AppointmentWidget -> {
                AppointmentWidgetCard(
                    headline = "Next Appointment",
                    appointmentTime = "10:00 AM",
                    appointmentDate = "Tomorrow, 26 October",
                    location = "St. Mary's Hospital",
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.TestResultWidget -> {
                TestResultWidgetCard(
                    headline = "Latest Test Result",
                    testName = "Blood Glucose",
                    result = "5.8 mmol/L",
                    resultDate = "Tested 24 October",
                    chartData = listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.65f, 0.8f, 0.7f),
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.HealthGoalsWidget -> {
                WidgetCard(
                    headline = "Health Goals",
                    subheadline = "7,500 of 10,000 steps",
                    progress = 0.75f,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.PrescriptionService -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.Medication,
                    title = "Prescriptions",
                    subtitle = "Order and track prescriptions",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.AppointmentService -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.CalendarMonth,
                    title = "Appointments",
                    subtitle = "View and manage appointments",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.TestResultService -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.Science,
                    title = "Test Results",
                    subtitle = "View your test results",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.VaccinationService -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.Vaccines,
                    title = "Vaccinations",
                    subtitle = "View your vaccination records",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.DocumentService -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.Description,
                    title = "Documents",
                    subtitle = "Access your health documents",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.NHS111Info -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.LocalHospital,
                    title = "NHS 111 Online",
                    subtitle = "Check if you need urgent medical help",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.HealthAtoZInfo -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.MenuBook,
                    title = "Health A to Z",
                    subtitle = "Browse conditions and treatments",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is WidgetItem.NHSServicesInfo -> {
                MenuWidgetCard(
                    icon = Icons.Outlined.LocationOn,
                    title = "NHS Services",
                    subtitle = "Find services near you",
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

// Sealed class to represent different widget types
sealed class WidgetItem {
    data object PrescriptionWidget : WidgetItem()
    data object AppointmentWidget : WidgetItem()
    data object TestResultWidget : WidgetItem()
    data object HealthGoalsWidget : WidgetItem()

    data class PrescriptionService(
        val navController: NavController,
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            navController.navigate(Routes.prescriptions2)
        }
    }

    data class AppointmentService(
        val navController: NavController,
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            navController.navigate(Routes.upcomingAndAastAppointments)
        }
    }

    data class TestResultService(
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            // Add navigation route when available
        }
    }

    data class VaccinationService(
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            // Add navigation route when available
        }
    }

    data class DocumentService(
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            // Add navigation route when available
        }
    }

    data class NHS111Info(
        val context: android.content.Context,
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)

            try {
                val builder = androidx.browser.customtabs.CustomTabsIntent.Builder()
                builder.setShowTitle(true)
                builder.setUrlBarHidingEnabled(true)

                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, android.net.Uri.parse("https://111.nhs.uk"))
            } catch (e: Exception) {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://111.nhs.uk"))
                context.startActivity(intent)
            }
        }
    }

    data class HealthAtoZInfo(
        val context: android.content.Context,
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)

            try {
                val builder = androidx.browser.customtabs.CustomTabsIntent.Builder()
                builder.setShowTitle(true)
                builder.setUrlBarHidingEnabled(true)

                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, android.net.Uri.parse("https://www.nhs.uk/conditions/"))
            } catch (e: Exception) {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://www.nhs.uk/conditions/"))
                context.startActivity(intent)
            }
        }
    }

    data class NHSServicesInfo(
        val context: android.content.Context,
        val view: android.view.View,
        val haptics: androidx.compose.ui.hapticfeedback.HapticFeedback
    ) : WidgetItem() {
        val onClick: () -> Unit = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)

            try {
                val builder = androidx.browser.customtabs.CustomTabsIntent.Builder()
                builder.setShowTitle(true)
                builder.setUrlBarHidingEnabled(true)

                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context, android.net.Uri.parse("https://www.nhs.uk/nhs-services/"))
            } catch (e: Exception) {
                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://www.nhs.uk/nhs-services/"))
                context.startActivity(intent)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}