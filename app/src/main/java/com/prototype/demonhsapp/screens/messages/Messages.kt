package com.prototype.demonhsapp.screens.messages

import android.view.SoundEffectConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.viewmodels.MessagesViewModel
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5

data class Message(
    val id: String,
    val sender: String,
    val subject: String,
    val preview: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val isImportant: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Messages(
    navController: NavController,
    viewModel: MessagesViewModel,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Filter state
    var showUnreadOnly by remember { mutableStateOf(false) }
    var showFilterMenu by remember { mutableStateOf(false) }

    // Get messages from ViewModel and filter if needed
    val messages = viewModel.messages.value
    val filteredMessages = if (showUnreadOnly) {
        messages.filter { !it.isRead }
    } else {
        messages
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    val unreadCount = viewModel.unreadCount
                    Text(
                        text = if (unreadCount > 0) "Messages ($unreadCount)" else "Messages",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = (24 + (32 - 24) * (1 - scrollBehavior.state.collapsedFraction)).sp,
                        fontWeight = FontWeight.Normal
                    )
                },
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter messages",
                            tint = nhsBlack
                        )
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("All messages") },
                            onClick = {
                                showUnreadOnly = false
                                showFilterMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Unread only") },
                            onClick = {
                                showUnreadOnly = true
                                showFilterMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey4.copy(alpha = 0.2f)
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(
                    items = filteredMessages,
                    key = { it.id }
                ) { message ->
                    SwipeableMessageItem(
                        message = message,
                        onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            // Mark as read when clicked
                            viewModel.markAsRead(message.id)
                            navController.navigate("message_detail/${message.id}")
                        },
                        onDelete = {
                            viewModel.deleteMessage(message.id)
                        },
                        onMarkUnread = {
                            viewModel.markAsUnread(message.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableMessageItem(
    message: Message,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onMarkUnread: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete()
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onMarkUnread()
                    false // Don't dismiss, just mark as unread
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                    SwipeToDismissBoxValue.StartToEnd -> nhsBlue.copy(alpha = 0.8f)
                    else -> Color.Transparent
                },
                label = "background color"
            )

            val scale by animateFloatAsState(
                targetValue = if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) 1.3f else 0.8f,
                label = "icon scale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 24.dp)
            ) {
                when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        // Delete action on right swipe
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .scale(scale)
                        )
                    }
                    SwipeToDismissBoxValue.StartToEnd -> {
                        // Mark as unread on left swipe
                        Icon(
                            imageVector = Icons.Default.MarkEmailUnread,
                            contentDescription = "Mark as unread",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .scale(scale)
                        )
                    }
                    else -> {}
                }
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true
    ) {
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                MessageListItem(
                    message = message,
                    onClick = onClick
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 72.dp),
                    color = nhsGrey4
                )
            }
        }
    }
}

@Composable
fun MessageListItem(
    message: Message,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar circle with initials
        Surface(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            color = nhsBlue.copy(alpha = 0.2f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = message.sender.first().uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = nhsBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Message content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.sender,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Bold,
                    color = nhsBlack,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (!message.isRead) {
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = "Unread",
                            tint = nhsBlue,
                            modifier = Modifier.size(8.dp)
                        )
                    }
                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = nhsGrey,
                        fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Bold
                    )
                }
            }

            Text(
                text = message.subject,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.SemiBold,
                color = nhsGrey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )

            Text(
                text = message.preview,
                style = MaterialTheme.typography.bodySmall,
                color = nhsGrey,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

fun getSampleMessages(): List<Message> {
    return listOf(
        Message(
            id = "1",
            sender = "NHS Leeds Teaching Hospitals",
            subject = "Appointment Confirmation",
            preview = "Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department.",
            timestamp = "9:30 AM",
            isRead = false
        ),
        Message(
            id = "2",
            sender = "Dr Sarah Johnson",
            subject = "Test Results Available",
            preview = "Your recent blood test results are now available to view. Please log in to your account to see the details.",
            timestamp = "Yesterday",
            isRead = false
        ),
        Message(
            id = "3",
            sender = "Oak Tree Surgery",
            subject = "Prescription Ready",
            preview = "Your repeat prescription is ready for collection at the pharmacy. Please collect within 7 days.",
            timestamp = "2 days ago",
            isRead = true
        ),
        Message(
            id = "4",
            sender = "NHS App Team",
            subject = "New Feature: Video Consultations",
            preview = "You can now book video consultations with your GP through the NHS App. Tap here to learn more.",
            timestamp = "Nov 1",
            isRead = true
        ),
        Message(
            id = "5",
            sender = "Manchester Royal Infirmary",
            subject = "Follow-up Appointment Needed",
            preview = "Please contact us to schedule your follow-up appointment following your recent consultation.",
            timestamp = "Oct 28",
            isRead = true
        ),
        Message(
            id = "6",
            sender = "Vaccination Centre",
            subject = "Flu Jab Reminder",
            preview = "It's that time of year again. Book your flu vaccination appointment at your local pharmacy.",
            timestamp = "Oct 25",
            isRead = true
        ),
        Message(
            id = "7",
            sender = "Dr Michael Chen",
            subject = "Medication Review Due",
            preview = "Your annual medication review is due. Please book an appointment with reception at your convenience.",
            timestamp = "Oct 20",
            isRead = true
        ),
        Message(
            id = "8",
            sender = "NHS 111",
            subject = "Your Recent Query",
            preview = "Thank you for using NHS 111 online. Based on your symptoms, we recommend you contact your GP surgery.",
            timestamp = "Oct 15",
            isRead = true
        )
    )
}

@Preview(showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun MessagesPreview() {
    Messages(rememberNavController(), MessagesViewModel(), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun MessageListItemPreview() {
    MessageListItem(
        message = Message(
            id = "1",
            sender = "NHS Leeds Teaching Hospitals",
            subject = "Appointment Confirmation",
            preview = "Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department.",
            timestamp = "9:30 AM",
            isRead = false
        ),
        onClick = {}
    )
}