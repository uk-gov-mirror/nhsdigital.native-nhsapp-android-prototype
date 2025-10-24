package com.nhs.online.nhsonline.proto.navigation


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nhs.online.nhsonline.proto.ui.theme.nhsGrey

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        BottomNavItem.navItems.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount > 0) {
                                Badge {
                                    Text(item.badgeCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selected)
                                item.selectedIcon
                            else
                                item.unselectedIcon,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(item.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    selectedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.16f),
                    disabledTextColor = nhsGrey,
                    disabledIconColor = nhsGrey
                )
            )
        }
    }
}
