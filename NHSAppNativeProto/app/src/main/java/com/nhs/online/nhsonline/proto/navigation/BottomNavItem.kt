package com.nhs.online.nhsonline.proto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int = 0
) {
    data object Home : BottomNavItem(Routes.home, "Home", Icons.Default.Home, unselectedIcon = Icons.Outlined.Home)
    data object Services : BottomNavItem(Routes.services, "Services", Icons.Default.LocalHospital, unselectedIcon = Icons.Outlined.LocalHospital)
    data object YourHealth : BottomNavItem(Routes.yourHealth, "Your health", Icons.Default.Favorite, unselectedIcon = Icons.Outlined.FavoriteBorder)
    data object Messages : BottomNavItem(Routes.messages, "Messages", Icons.Default.Email, unselectedIcon = Icons.Outlined.Email, badgeCount = 2)

    companion object {
        val navItems = listOf(Home, Services, YourHealth, Messages)
    }
}