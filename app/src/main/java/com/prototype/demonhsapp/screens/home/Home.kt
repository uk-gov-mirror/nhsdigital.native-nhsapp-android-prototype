package com.prototype.demonhsapp.screens.home

import android.view.SoundEffectConstants
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.prototype.demonhsapp.R
import com.prototype.demonhsapp.components.*
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()

    // Get screen configuration for height calculation
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val blueSectionHeight = screenHeight * 0.5f

    // Get today's date
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.UK)
    val formattedDate = today.format(dateFormatter)

    // Calculate if we should show grey background based on scroll
    val showGreyBackground = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                    (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset > 400)
        }
    }

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
                            color = if (showGreyBackground.value) nhsBlack else Color.White,
                            fontSize = (20 + (28 - 20) * (1 - scrollBehavior.state.collapsedFraction)).sp
                        )
                        if (scrollBehavior.state.collapsedFraction < 0.5f) {
                            Text(
                                formattedDate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (showGreyBackground.value) nhsGrey else Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    // NHS Logo - only visible when expanded
                    if (scrollBehavior.state.collapsedFraction < 0.5f) {
                        Image(
                            painter = painterResource(id = R.drawable.nhs_logo_2),
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
                    containerColor = if (showGreyBackground.value) nhsGrey5 else nhsBlue,
                    scrolledContainerColor = if (showGreyBackground.value) nhsGrey5 else nhsBlue,
                    actionIconContentColor = if (showGreyBackground.value) nhsBlack else Color.White
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
            // Blue Gradient Header Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(blueSectionHeight)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(nhsBlue, nhsDarkBlue)
                            )
                        )
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(blueSectionHeight * 0.75f),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Prescriptions card
                            item {
                                WidgetCard(
                                    headline = "Prescriptions",
                                    subheadline = "2 ready to collect",
                                    progress = 0.67f,
                                    modifier = Modifier
                                        .width(280.dp)
                                        .fillMaxHeight()
                                )
                            }

                            // Appointments card
                            item {
                                AppointmentWidgetCard(
                                    headline = "Next Appointment",
                                    appointmentTime = "10:00 AM",
                                    appointmentDate = "Tomorrow, 26 October",
                                    location = "St. Mary's Hospital",
                                    modifier = Modifier
                                        .width(280.dp)
                                        .fillMaxHeight()
                                )
                            }

                            // Test Results card
                            item {
                                TestResultWidgetCard(
                                    headline = "Latest Test Result",
                                    testName = "Blood Glucose",
                                    result = "5.8 mmol/L",
                                    resultDate = "Tested 24 October",
                                    chartData = listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.65f, 0.8f, 0.7f),
                                    modifier = Modifier
                                        .width(280.dp)
                                        .fillMaxHeight()
                                )
                            }

                            // Health Goals card
                            item {
                                WidgetCard(
                                    headline = "Health Goals",
                                    subheadline = "7,500 of 10,000 steps",
                                    progress = 0.75f,
                                    modifier = Modifier
                                        .width(280.dp)
                                        .fillMaxHeight()
                                )
                            }

                            // Vaccinations card
                            item {
                                WidgetCard(
                                    headline = "Vaccinations",
                                    subheadline = "Up to date",
                                    progress = 1.0f,
                                    modifier = Modifier
                                        .width(280.dp)
                                        .fillMaxHeight()
                                )
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