package com.prototype.nhsappcv1.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappcv1.ui.theme.nhsBlack
import com.prototype.nhsappcv1.ui.theme.nhsGrey
import com.prototype.nhsappcv1.ui.theme.nhsGrey4
import com.prototype.nhsappcv1.ui.theme.nhsGrey5


@Preview (showBackground = true )
@Composable
fun ExampleList () {
    Column {
        ListItem(
            headlineContent = { Text("Dave's account") },
            leadingContent = {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "Localized description",
                )
            },
            overlineContent = {Text("Secondary profile")},
            trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)},
            supportingContent = {Text("Change your profile and ask for permissions to access other profiles")},
            colors = ListItemDefaults.colors(headlineColor = nhsBlack, supportingColor = nhsGrey, overlineColor = nhsGrey, leadingIconColor = nhsGrey, trailingIconColor = nhsGrey)

        )
        HorizontalDivider()
        ListItem(
            headlineContent = { Text("Change settings") },
            leadingContent = {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = "Localized description",
                )
            },
            trailingContent = { Badge { Text("24") } }
        )
        HorizontalDivider()
    }
}

// TODO: Need to fix list data loop
data class Task (val id: Int, val name: String, val icon: ImageVector)

val Tasks = listOf(
    Task(1, "Buy food", Icons.Outlined.ShoppingCart),
    Task(2, "Call mum", Icons.Outlined.Phone),
    Task(3, "Finish kotlin list", Icons.Outlined.Build),
    Task(4, "Go running", Icons.Outlined.Notifications),
    Task(5, "Read book", Icons.Outlined.Menu)
)


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun TaskList (modifier: Modifier = Modifier, list: List<Task> = remember {Tasks}) {
    LazyColumn(modifier = modifier) {
        items(list) { task -> Text("$Tasks.name") }
    }
}

// items(items = list, key = {task -> task.id} ) { task -> WellnessTaskItem(taskName = task.label) }


@OptIn(ExperimentalMaterial3Api::class)
@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ExampleListScreen () {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { },
        bottomBar = { },
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
                            Text("Sub header here", fontSize = 16.sp, fontWeight = FontWeight.Medium)
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