package com.prototype.demonhsapp.screens.home

import android.view.SoundEffectConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.prototype.demonhsapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.AppointmentWidgetCard
import com.prototype.demonhsapp.components.CampaignCard
import com.prototype.demonhsapp.components.CardData
import com.prototype.demonhsapp.components.ChromeCustomTab
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.components.TestResultWidgetCard
import com.prototype.demonhsapp.components.WebViewButton
import com.prototype.demonhsapp.components.WidgetCard
import com.prototype.demonhsapp.components.WidgetCardLazyRow
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsDarkBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import com.prototype.demonhsapp.ui.theme.nhsPurple
import com.prototype.demonhsapp.ui.theme.nhsYellow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Get today's date
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.UK)
    val formattedDate = today.format(dateFormatter)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = (LocalConfiguration.current.screenHeightDp / 2).dp,
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .background(nhsBlack, RoundedCornerShape(2.dp))
                )
            }
        },
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(nhsGrey5)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // List items
                item {
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
                                        navController.navigate(Routes.prescriptions)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
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
                        }
                        Column {
                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        navController.navigate(Routes.upcomingAndAastAppointments)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
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
                        }
                        Column {
                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
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
                        }
                        Column {
                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.Email,
                                        contentDescription = null,
                                        tint = nhsGrey,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        "Messages",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = nhsBlack
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        "Read messages from your GP",
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
                        }
                        Column {
                            ListItem(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    })
                                    .padding(vertical = 8.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = Color.Transparent
                                ),
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
                }

                // Section title for NHS web links
                item {
                    Text(
                        text = "NHS information and support",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                        color = nhsBlack
                    )
                }

                // NHS Web Links List
                item {
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
                }
            }
        },
        sheetContainerColor = nhsGrey5,
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

        }
    ) { paddingValues ->
        // Main content - Scrollable LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(nhsBlue, nhsDarkBlue)
                    )
                )
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = 64.dp
            )
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    // NHS Logo
                    Image(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .height(22.dp)
                            .width(54.dp),
                        painter = painterResource(R.drawable.nhs_logo_2),
                        contentDescription = "NHS Logo",
                        contentScale = ContentScale.FillWidth
                    )

                    // Summary title
                    Text(
                        "Summary",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )

                    // Date
                    Text(
                        formattedDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                // Widget Cards Lazy Row with mixed card types
                val listState = rememberLazyListState()

                // Track scroll position and trigger haptics on card change
                LaunchedEffect(listState) {
                    var previousFirstVisibleIndex = 0

                    snapshotFlow { listState.firstVisibleItemIndex }
                        .collect { currentIndex ->
                            if (currentIndex != previousFirstVisibleIndex) {
                                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                previousFirstVisibleIndex = currentIndex
                            }
                        }
                }

                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Prescriptions card
                    item {
                        WidgetCard(
                            headline = "Prescriptions",
                            subheadline = "2 ready to collect",
                            progress = 0.67f
                        )
                    }

                    // Appointments card
                    item {
                        AppointmentWidgetCard(
                            headline = "Next Appointment",
                            appointmentTime = "10:00 AM",
                            appointmentDate = "Tomorrow, 26 October",
                            location = "St. Mary's Hospital"
                        )
                    }

                    // Test Results card
                    item {
                        TestResultWidgetCard(
                            headline = "Latest Test Result",
                            testName = "Blood Glucose",
                            result = "5.8 mmol/L",
                            resultDate = "Tested 24 October",
                            chartData = listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.65f, 0.8f, 0.7f)
                        )
                    }

                    // Health Goals card
                    item {
                        WidgetCard(
                            headline = "Health Goals",
                            subheadline = "7,500 of 10,000 steps",
                            progress = 0.75f
                        )
                    }

                    // Vaccinations card
                    item {
                        WidgetCard(
                            headline = "Vaccinations",
                            subheadline = "Up to date",
                            progress = 1.0f
                        )
                    }
                }
            }
        }
    }
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}