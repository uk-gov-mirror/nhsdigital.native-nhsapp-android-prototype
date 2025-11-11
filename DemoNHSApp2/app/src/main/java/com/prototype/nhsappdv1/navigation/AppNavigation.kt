package com.prototype.nhsappdv1.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prototype.nhsappdv1.viewmodels.MessagesViewModel
import com.prototype.nhsappdv1.screens.accountsettings.AccountSettings
import com.prototype.nhsappdv1.screens.ScreenA
import com.prototype.nhsappdv1.screens.ScreenB
import com.prototype.nhsappdv1.screens.home.Home
import com.prototype.nhsappdv1.screens.messages.Messages
import com.prototype.nhsappdv1.screens.messages.MessageDetail
import com.prototype.nhsappdv1.screens.messages.ArchivedMessages
import com.prototype.nhsappdv1.screens.messages.yourmessages.YourMessages
import com.prototype.nhsappdv1.screens.services.Services
import com.prototype.nhsappdv1.screens.services.prescriptions.Prescriptions
import com.prototype.nhsappdv1.screens.yourhealth.YourHealth
import com.prototype.nhsappdv1.screens.yourhealth.appointments.Referrals
import com.prototype.nhsappdv1.screens.yourhealth.appointments.UpcomingAndPastAppointments
import com.prototype.nhsappdv1.screens.yourhealth.prescriptions.CheckPrescriptions
import com.prototype.nhsappdv1.screens.yourhealth.prescriptions.PastPrescriptions
import com.prototype.nhsappdv1.screens.yourhealth.prescriptions.Prescriptions2
import com.prototype.nhsappdv1.screens.yourhealth.prescriptions.PrescriptionDetail
import com.prototype.nhsappdv1.screens.yourhealth.prescriptions.ViewManagePrescriptions
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey
import com.prototype.nhsappdv1.ui.theme.nhsGrey5


@Composable
fun AppNavigation(){

    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    // Messages ViewModel for shared state
    val messagesViewModel: MessagesViewModel = viewModel()

    //data class which defines variables for the list

    data class navItem<T : Any> (
        val label :String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val route: T,
        val hasNews: Boolean,
        val badgeCount: Int? = null
    )

    // Add the navigation controller
    val navController = rememberNavController()

    // List of navigation bar items to loop through - now only 3 main destinations
    val navItemList = listOf(
        navItem("Summary", Icons.Default.Dashboard, Icons.Outlined.Dashboard, "home", false),
        navItem("Messages", Icons.Default.Chat, Icons.Outlined.Chat, "messages", false,
            if (messagesViewModel.unreadCount > 0) messagesViewModel.unreadCount else null),
        navItem("Profile", Icons.Default.Person, Icons.Outlined.Person, "profile", false)
    )

    // remember the state of the navigation bar item
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Sync selectedIndex with current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if we should hide the bottom bar
    val shouldShowBottomBar = currentRoute?.startsWith("prescriptionDetail") != true

    // Update selectedIndex based on current route
    selectedIndex = when (currentRoute) {
        "home" -> 0
        "messages" -> 1
        "profile" -> 2
        else -> {
            // For child screens, maintain the parent tab selection
            when {
                currentRoute?.startsWith("message_detail") == true -> 1
                currentRoute == "archived_messages" -> 1
                currentRoute?.startsWith("prescriptionDetail") == true -> 0
                currentRoute == Routes.prescriptions2 -> 0
                currentRoute == Routes.upcomingAndAastAppointments -> 0
                else -> selectedIndex
            }
        }
    }

    // Prevent back navigation on main destinations
    val isMainDestination = currentRoute in listOf("home", "messages", "profile")
    BackHandler(enabled = isMainDestination) {
        // Do nothing - prevent back navigation on main screens
    }

    // Scaffold to host the navigation bar and graph
    Scaffold(
        topBar = { },
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(containerColor = nhsGrey5) {

                    navItemList.forEachIndexed { index, navItem ->
                        // Animate scale when selected
                        val scale by animateFloatAsState(
                            targetValue = if (selectedIndex == index) 1.1f else 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                            label = "scale"
                        )

                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
//                            view.playSoundEffect(SoundEffectConstants.CLICK)
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (navItem.badgeCount != null) {
                                            Badge{ Text(text = navItem.badgeCount.toString()) }
                                        } else if (navItem.hasNews) {
                                            Badge()
                                        }
                                    },
                                    modifier = Modifier.graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (index == selectedIndex) {navItem.selectedIcon} else navItem.unselectedIcon,
                                        contentDescription = null
                                    )
                                }
                            },
                            label = { Text(text = navItem.label)},
                            colors = NavigationBarItemColors(
                                selectedIconColor = nhsBlue,
                                unselectedIconColor = nhsGrey,
                                selectedTextColor = nhsBlue,
                                unselectedTextColor = nhsGrey,
                                selectedIndicatorColor = nhsBlue.copy(alpha = 0.2f),
                                disabledTextColor = nhsGrey,
                                disabledIconColor = nhsGrey
                            )
                        )
                    }
                }
            }
        },
        content = { padding ->
            // Map the navigation host to specific screens
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(bottom = if (shouldShowBottomBar) 88.dp else 0.dp),
                enterTransition = { slideIntoContainer(animationSpec = tween(300, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) },
                popExitTransition = { slideOutOfContainer(animationSpec = tween(300, easing = EaseOut), towards = AnimatedContentTransitionScope.SlideDirection.End) },
                builder = {

                    //Examples
                    composable(route = Routes.screenA){ ScreenA(navController, modifier = Modifier) }
                    composable(route = Routes.screenB){ ScreenB() }

                    // Main destinations - 3 primary screens
                    composable(route = "home", enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Home(navController, modifier = Modifier) }
                    composable(route = "messages", enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Messages(navController, messagesViewModel, modifier = Modifier) }
                    composable(route = "profile", enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ AccountSettings(navController, modifier = Modifier) }

                    // Additional screens accessible from main destinations
                    composable(route = "services"){ Services(navController, modifier = Modifier) }
                    composable(route = "your_health"){ YourHealth(navController, modifier = Modifier) }

                    // Children screens
                    composable(route = Routes.yourMessages){ YourMessages(navController) }
                    composable(route = "archived_messages"){ ArchivedMessages(navController, messagesViewModel, modifier = Modifier) }
                    composable(route = Routes.prescriptions){ Prescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.prescriptions2){ Prescriptions2(navController, modifier = Modifier) }
                    composable(route = Routes.viewManagePrescriptions){ ViewManagePrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.checkPrescriptions){ CheckPrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.pastPrescriptions){ PastPrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.upcomingAndAastAppointments){ UpcomingAndPastAppointments(navController, modifier = Modifier) }
                    composable(route = Routes.referrals){ Referrals(navController, modifier = Modifier) }

                    // Message Detail with argument
                    composable(
                        route = "message_detail/{messageId}",
                        arguments = listOf(
                            navArgument("messageId") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val messageId = backStackEntry.arguments?.getString("messageId") ?: ""
                        MessageDetail(
                            navController = navController,
                            messageId = messageId
                        )
                    }

                    // Prescription Detail with zoom scale animation (modal style)
                    composable(
                        route = "prescriptionDetail/{medicationName}/{dosage}/{status}/{prescribedDate}/{prescribedBy}/{quantity}",
                        arguments = listOf(
                            navArgument("medicationName") { type = NavType.StringType },
                            navArgument("dosage") { type = NavType.StringType },
                            navArgument("status") { type = NavType.StringType },
                            navArgument("prescribedDate") { type = NavType.StringType },
                            navArgument("prescribedBy") { type = NavType.StringType },
                            navArgument("quantity") { type = NavType.StringType }
                        ),
                        enterTransition = {
                            scaleIn(
                                initialScale = 0.9f,
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        exitTransition = {
                            scaleOut(
                                targetScale = 1.0f,
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) + fadeOut(animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            scaleIn(
                                initialScale = 1.0f,
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) + fadeIn(animationSpec = tween(300))
                        },
                        popExitTransition = {
                            scaleOut(
                                targetScale = 0.9f,
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) + fadeOut(animationSpec = tween(300))
                        }
                    ) { backStackEntry ->
                        PrescriptionDetail(
                            navController = navController,
                            medicationName = backStackEntry.arguments?.getString("medicationName") ?: "",
                            dosage = backStackEntry.arguments?.getString("dosage") ?: "",
                            status = backStackEntry.arguments?.getString("status") ?: "",
                            prescribedDate = backStackEntry.arguments?.getString("prescribedDate") ?: "",
                            prescribedBy = backStackEntry.arguments?.getString("prescribedBy") ?: "",
                            quantity = backStackEntry.arguments?.getString("quantity") ?: ""
                        )
                    }
                }
            )
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun AppNavPreview() {
    AppNavigation()
}