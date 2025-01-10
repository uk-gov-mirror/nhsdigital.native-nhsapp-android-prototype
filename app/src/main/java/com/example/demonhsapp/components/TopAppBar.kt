package com.example.demonhsapp.components


import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demonhsapp.navigation.Routes
import com.example.demonhsapp.ui.theme.nhsBlue
import com.example.demonhsapp.ui.theme.nhsGrey
import com.example.demonhsapp.ui.theme.nhsGrey4
import com.example.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@androidx.compose.ui.tooling.preview.Preview
@Composable
fun ExitUntilCollapsedLargeTopAppBar() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Large TopAppBar", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun NHSAppBar() {
    MediumTopAppBar(
        title = {
            Text("Services", maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back to previous screen"
                        )
                    }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = Color.White),
//        scrollBehavior = scrollBehavior
    )
}

/**
 * A sample for a simple use of small [TopAppBar].
 *
 * The top app bar here does not react to any scroll events in the content under it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun NHSTopAppBarExample() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Services", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(
//                            imageVector = Icons.Filled.Menu,
//                            contentDescription = "Localized description"
//                        )
//                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar(containerColor = nhsBlue, modifier = Modifier) {
                NavigationBarItem(icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)}, label = {Text("Home")}, selected = true, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = Color.White, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White.copy(alpha = 0.16f), disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null)}, label = {Text("Services")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)}, label = {Text("Your health")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))
                NavigationBarItem(icon = {Icon(imageVector = Icons.Outlined.Email, contentDescription = null)}, label = {Text("Messages")}, selected = false, onClick = {}, colors = NavigationBarItemColors(selectedIconColor = nhsBlue, unselectedIconColor = Color.White, selectedTextColor = Color.White, unselectedTextColor = Color.White, selectedIndicatorColor = Color.White, disabledTextColor = nhsGrey, disabledIconColor = nhsGrey))

            }
        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(values).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            Text("Get care and support to help you stay well", fontSize = 16.sp)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column (Modifier.padding(bottom = 16.dp)) {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Sub header here", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }
                    item() {

                        Column () {
                            ListItem(
                                modifier = Modifier.clickable(onClick = {/*TODO*/}),
                                colors = ListItemDefaults.colors(Color.White) ,
                                headlineContent = { Text("Dave's account") },
                                leadingContent = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        contentDescription = "Localized description",
                                    )
                                },
                                overlineContent = {Text("Secondary profile")},
                                trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
                                supportingContent = {Text("Change your profile and ask for permissions to access other profiles")}

                            )
                            HorizontalDivider(color = nhsGrey4)
                        }
                    }


                }
            }
        }
    )
}