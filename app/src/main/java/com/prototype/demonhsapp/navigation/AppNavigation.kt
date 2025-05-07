package com.prototype.demonhsapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
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
import androidx.compose.ui.graphics.Color
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
import com.prototype.demonhsapp.navigation.Routes.home
import com.prototype.demonhsapp.navigation.Routes.messages
import com.prototype.demonhsapp.navigation.Routes.profile
import com.prototype.demonhsapp.navigation.Routes.services
import com.prototype.demonhsapp.screens.ScreenA
import com.prototype.demonhsapp.screens.ScreenB
import com.prototype.demonhsapp.screens.home.Home
import com.prototype.demonhsapp.screens.messages.Messages
import com.prototype.demonhsapp.screens.messages.yourmessages.MessageDetail
import com.prototype.demonhsapp.screens.messages.yourmessages.YourMessages
import com.prototype.demonhsapp.screens.profile.Profile
import com.prototype.demonhsapp.screens.services.Services
import com.prototype.demonhsapp.screens.services.prescriptions.Prescriptions
import com.prototype.demonhsapp.screens.profile.appointments.Referrals
import com.prototype.demonhsapp.screens.profile.appointments.UpcomingAndPastAppointments
import com.prototype.demonhsapp.screens.profile.prescriptions.CheckPrescriptions
import com.prototype.demonhsapp.screens.profile.prescriptions.PastPrescriptions
import com.prototype.demonhsapp.screens.profile.prescriptions.Prescriptions2
import com.prototype.demonhsapp.screens.profile.prescriptions.PrescriptionsDetail
import com.prototype.demonhsapp.screens.profile.prescriptions.ViewManagePrescriptions
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey


@Composable
fun AppNavigation(){

    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

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

    // List of navigation bar items to loop through
    val navItemList = listOf(
        navItem ("Home", Icons.Default.Home, Icons.Outlined.Home, home, false),
//        navItem("Services", Icons.Default.LocalHospital, Icons.Outlined.LocalHospital, services, false),
        navItem("Profile", Icons.Default.AccountCircle, Icons.Outlined.AccountCircle, profile, false),
        navItem("Messages", Icons.Default.Email, Icons.Outlined.Email, messages, false, 2)
    )

    // remember the state of the navigation bar item
    var selectedIndex by remember { mutableIntStateOf(0) }

    // Scaffold to host the navigation bar and graph
    Scaffold(
        topBar = { },
        bottomBar = {
            NavigationBar(containerColor = nhsBlue) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id){ saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
//                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                  },
                        icon = { BadgedBox(badge = { if (navItem.badgeCount != null) {Badge{ Text(text = navItem.badgeCount.toString()) }} else if (navItem.hasNews) { Badge() } }) { Icon(imageVector = if (index == selectedIndex) {navItem.selectedIcon} else navItem.unselectedIcon, contentDescription = null) } },
                        label = { Text(text = navItem.label)},
                        colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey)
                    )
                }
            }
        },
        content = { padding ->
            // Map the navigation host to specific screens
            NavHost(
                navController = navController,
                startDestination = Routes.home,
                modifier = Modifier.padding(bottom = 88.dp),
//        enterTransition = { fadeIn(animationSpec = tween(700, easing = LinearEasing)) + slideIntoContainer(animationSpec = tween(300, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Start) },
//        exitTransition = { fadeOut(animationSpec = tween(700, easing = LinearEasing)) + slideOutOfContainer(animationSpec = tween(300, easing = EaseOut), towards = AnimatedContentTransitionScope.SlideDirection.End) },
//        popEnterTransition = { fadeIn(animationSpec = tween(700, easing = LinearEasing)) + slideIntoContainer(animationSpec = tween(300, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.End) },
//        popExitTransition = { fadeOut(animationSpec = tween(700, easing = LinearEasing)) + slideOutOfContainer(animationSpec = tween(300, easing = EaseOut), towards = AnimatedContentTransitionScope.SlideDirection.Start) },
                enterTransition = { slideIntoContainer(animationSpec = tween(300, easing = EaseIn), towards = AnimatedContentTransitionScope.SlideDirection.Start) },
                exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) },
                popEnterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) },
                popExitTransition = { slideOutOfContainer(animationSpec = tween(300, easing = EaseOut), towards = AnimatedContentTransitionScope.SlideDirection.End) },
                builder = {

                    //Examples
                    composable(route = Routes.screenA){ ScreenA(navController, modifier = Modifier) }
                    composable(route = Routes.screenB){ ScreenB() }

                    // Main hubs
                    composable(route = Routes.home, enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Home(navController, modifier = Modifier) }
                    composable(route = Routes.services, enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Services(navController, modifier = Modifier) }
                    composable(route = Routes.profile, enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Profile(navController, modifier = Modifier) }
                    composable(route = Routes.messages, enterTransition = { fadeIn(animationSpec = tween(300, easing = LinearEasing)) }, exitTransition = { fadeOut(animationSpec = tween(300, easing = LinearEasing)) } ){ Messages(navController, modifier = Modifier) }

                    // Children screens
                    composable(route = Routes.yourMessages){ YourMessages(navController) }
                    composable(route = Routes.prescriptions){ Prescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.prescriptions2){ Prescriptions2(navController, modifier = Modifier) }
                    composable(route = Routes.viewManagePrescriptions){ ViewManagePrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.checkPrescriptions){ CheckPrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.pastPrescriptions){ PastPrescriptions(navController, modifier = Modifier) }
                    composable(route = Routes.prescriptionsDetail){ PrescriptionsDetail(navController, modifier = Modifier) }
                    composable(route = Routes.upcomingAndAastAppointments){ UpcomingAndPastAppointments(navController, modifier = Modifier) }
                    composable(route = Routes.referrals){ Referrals(navController, modifier = Modifier) }
                    composable(route = Routes.messageDetail){ MessageDetail(navController, modifier = Modifier) }

                    // Independent screens

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