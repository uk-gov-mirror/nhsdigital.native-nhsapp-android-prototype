package com.prototype.nhsappcv1.screens.home

import android.view.SoundEffectConstants
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.components.*
import com.prototype.nhsappcv1.navigation.Routes
import com.prototype.nhsappcv1.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.prototype.nhsappcv1.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Get today's date
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.UK)
    val formattedDate = today.format(dateFormatter)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "Summary",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Medium,
                            color = nhsBlack,
                            fontSize = (20 + (28 - 20) * (1 - scrollBehavior.state.collapsedFraction)).sp
                        )
                        if (scrollBehavior.state.collapsedFraction < 0.5f) {
                            Text(
                                formattedDate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = nhsGrey,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    // NHS Logo - only visible when expanded
                    if (scrollBehavior.state.collapsedFraction < 0.5f) {
                        Image(
                            painter = painterResource(id = R.drawable.nhs_logo),
                            contentDescription = "NHS Logo",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(48.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                actions = {

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey5,
                    actionIconContentColor = nhsBlack
                ),
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = nhsGrey5
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header Section (Grey background, white cards)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                // Parallax effect - content moves slower than scroll
                                val scrollOffset = if (listState.firstVisibleItemIndex == 0) {
                                    listState.firstVisibleItemScrollOffset.toFloat()
                                } else {
                                    0f
                                }
                                translationY = scrollOffset * 0.5f

                                // Fade out effect as you scroll
                                alpha = (1f - (scrollOffset / 500f)).coerceIn(0f, 1f)

                                // Scale down effect - content shrinks as you scroll
                                val scale = (1f - (scrollOffset / 1000f)).coerceIn(0.8f, 1f)
                                scaleX = scale
                                scaleY = scale
                            },
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Widget Cards Lazy Row with mixed card types
                        val widgetListState = rememberLazyListState()
                        val snapBehavior = rememberSnapFlingBehavior(lazyListState = widgetListState)

                        // Track scroll position and trigger haptics on card change
                        LaunchedEffect(widgetListState) {
                            var previousFirstVisibleIndex = 0

                            snapshotFlow { widgetListState.firstVisibleItemIndex }
                                .collect { currentIndex ->
                                    if (currentIndex != previousFirstVisibleIndex) {
                                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        previousFirstVisibleIndex = currentIndex
                                    }
                                }
                        }

                        LazyRow(
                            state = widgetListState,
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            flingBehavior = snapBehavior
                        ) {
                            items(5) { index ->
                                // Calculate which card is most visible (closest to center of screen)
                                val layoutInfo = widgetListState.layoutInfo
                                val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2

                                val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
                                val itemCenter = itemInfo?.let { (it.offset + it.size / 2) } ?: 0

                                // Check if this item's center is closest to viewport center
                                val isMostVisible = layoutInfo.visibleItemsInfo.minByOrNull { item ->
                                    kotlin.math.abs((item.offset + item.size / 2) - viewportCenter)
                                }?.index == index

                                val scale by animateFloatAsState(
                                    targetValue = if (isMostVisible) 1.05f else 1.0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    ),
                                    label = "cardScale$index"
                                )

                                Box(
                                    modifier = Modifier.graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                ) {
                                    when (index) {
                                        0 -> PrescriptionWidgetCard(
                                            prescriptionType = "Repeat prescription",
                                            medicationName = "Omeprazole",
                                            status = "Ready to collect",
                                            onClick = {
                                                navController.navigate(
                                                    "prescriptionDetail/Omeprazole/" +
                                                            "20mg capsules - Take one daily before food/" +
                                                            "READY/" +
                                                            "20 October 2025/" +
                                                            "Dr. Emily White/" +
                                                            "28 capsules"
                                                )
                                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                            }
                                        )
                                        1 -> AppointmentWidgetCard(
                                            headline = "Next Appointment",
                                            appointmentTime = "10:00 AM",
                                            appointmentDate = "Tomorrow, 26 October",
                                            location = "St. Mary's Hospital"
                                        )
                                        2 -> TestResultWidgetCard(
                                            headline = "Latest Test Result",
                                            testName = "Blood Glucose",
                                            result = "5.8 mmol/L",
                                            resultDate = "Tested 24 October",
                                            chartData = listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.65f, 0.8f, 0.7f)
                                        )
                                        3 -> WidgetCard(
                                            headline = "Health Goals",
                                            subheadline = "7,500 of 10,000 steps",
                                            progress = 0.75f
                                        )
                                        4 -> VaccinationWidgetCard(
                                            statusText = "Up to date",
                                            isUpToDate = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Grey Background Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(nhsGrey5)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    // Main Services Card
                    Card(
                        Modifier.padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column {
                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        navController.navigate(Routes.prescriptions2)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Medication,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Prescriptions",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "Order and track prescriptions",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        Icons.Outlined.ChevronRight,
                                        contentDescription = null,
                                        tint = nhsGrey2,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                tonalElevation = 0.dp
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        navController.navigate(Routes.upcomingAndAastAppointments)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.CalendarMonth,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Appointments",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "View and manage appointments",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        Icons.Outlined.ChevronRight,
                                        contentDescription = null,
                                        tint = nhsGrey2,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                tonalElevation = 0.dp
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Science,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Test results",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "View your test results",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        Icons.Outlined.ChevronRight,
                                        contentDescription = null,
                                        tint = nhsGrey2,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                tonalElevation = 0.dp
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Vaccines,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Vaccinations",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "View your vaccination records",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        Icons.Outlined.ChevronRight,
                                        contentDescription = null,
                                        tint = nhsGrey2,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                tonalElevation = 0.dp
                            )
                            HorizontalDivider(
                                color = nhsGrey4.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Description,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Documents",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "Access your health documents",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = nhsGrey
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        Icons.Outlined.ChevronRight,
                                        contentDescription = null,
                                        tint = nhsGrey2,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                tonalElevation = 0.dp
                            )
                        }
                    }

                    // Section title for NHS web links
                    Text(
                        text = "NHS information and support",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        color = nhsBlack
                    )

                    // NHS Web Links List
                    Card(
                        Modifier.padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(28.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        ChromeCustomTab(
                            text = "Check if you need urgent medical help using 111 online",
                            url = "https://111.nhs.uk"
                        )
                        ChromeCustomTab(
                            text = "Health A to Z - Browse conditions and treatments",
                            url = "https://www.nhs.uk/health-a-to-z/"
                        )
                        ChromeCustomTab(
                            text = "NHS Services - Find services near you",
                            url = "https://www.nhs.uk/nhs-services/",
                            showDivider = false
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}