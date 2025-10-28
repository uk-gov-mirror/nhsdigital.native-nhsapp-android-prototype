package com.prototype.demonhsapp.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Vaccines
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AppointmentWidgetCard
import com.prototype.demonhsapp.components.TestResultWidgetCard
import com.prototype.demonhsapp.components.WidgetCard
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsDarkBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import demonhsappkmm.composeapp.generated.resources.Res
import demonhsappkmm.composeapp.generated.resources.nhs_logo_2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()



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
                                "27 OCTOBER 2025",
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
                            painter = painterResource(Res.drawable.nhs_logo_2),
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
                        .height(256.dp)
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
                                .height(192.dp),
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
//                        ChromeCustomTab(
//                            text = "Check if you need urgent medical help using 111 online",
//                            url = "https://111.nhs.uk"
//                        )
//                        ChromeCustomTab(
//                            text = "Health A to Z - Browse conditions and treatments",
//                            url = "https://www.nhs.uk/health-a-to-z/"
//                        )
//                        ChromeCustomTab(
//                            text = "NHS Services - Find services near you",
//                            url = "https://www.nhs.uk/nhs-services/",
//                            showDivider = false
//                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}