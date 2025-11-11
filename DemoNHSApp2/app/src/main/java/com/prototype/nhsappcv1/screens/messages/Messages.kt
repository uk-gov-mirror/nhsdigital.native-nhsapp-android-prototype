package com.prototype.nhsappcv1.screens.messages

import android.view.SoundEffectConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import com.prototype.nhsappcv1.viewmodels.MessagesViewModel
import com.prototype.nhsappcv1.ui.theme.nhsBlack
import com.prototype.nhsappcv1.ui.theme.nhsBlue
import com.prototype.nhsappcv1.ui.theme.nhsGrey
import com.prototype.nhsappcv1.ui.theme.nhsGrey4
import com.prototype.nhsappcv1.ui.theme.nhsGrey5



/**
 * Data class representing a message in the NHS app
 *
 * IMPORTANT: This must be a data class (not just a class) to support
 * the copy() function which is used for updating state immutably
 */
data class Message(
    val id: String,
    val sender: String,
    val subject: String,
    val preview: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val isFlagged: Boolean = false,
    val hasAttachment: Boolean = false,
    val isArchived: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Messages(
    navController: NavController,
    viewModel: MessagesViewModel,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Tab state
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Inbox", "Archived")

    // Filter state
    var filterType by remember { mutableStateOf("all") } // "all", "unread", "flagged"
    var showFilterMenu by remember { mutableStateOf(false) }

    // Multi-select state
    var isMultiSelectMode by remember { mutableStateOf(false) }
    var selectedMessages by remember { mutableStateOf(setOf<String>()) }

    // Get messages from ViewModel - observing state for automatic updates
    val allMessages by viewModel.messages.collectAsState()

    // Separate messages into inbox and archived
    val inboxMessages = allMessages.filter { !it.isArchived }
    val archivedMessages = allMessages.filter { it.isArchived }

    // Get unread count for inbox only
    val unreadCount = inboxMessages.count { !it.isRead }

    // Current messages based on selected tab
    val currentMessages = when (selectedTabIndex) {
        0 -> inboxMessages
        1 -> archivedMessages
        else -> inboxMessages
    }

    // Apply filter based on filter type
    val filteredMessages = when (filterType) {
        "unread" -> currentMessages.filter { !it.isRead }
        "flagged" -> currentMessages.filter { it.isFlagged }
        else -> currentMessages
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = nhsGrey5,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = if (isMultiSelectMode) "${selectedMessages.size} selected" else "Messages",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    if (isMultiSelectMode) {
                        IconButton(onClick = {
                            isMultiSelectMode = false
                            selectedMessages = emptySet()
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Exit multi-select",
                                tint = nhsBlack
                            )
                        }
                    }
                },
                actions = {
                    if (isMultiSelectMode) {
                        // Multi-select actions
                        if (selectedTabIndex == 0) {
                            // Archive action for inbox
                            IconButton(
                                onClick = {
                                    selectedMessages.forEach { messageId ->
                                        viewModel.archiveMessage(messageId)
                                    }
                                    isMultiSelectMode = false
                                    selectedMessages = emptySet()
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                },
                                enabled = selectedMessages.isNotEmpty()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Archive,
                                    contentDescription = "Archive selected",
                                    tint = if (selectedMessages.isNotEmpty()) nhsBlack else nhsGrey
                                )
                            }
                        } else {
                            // Unarchive action for archived messages
                            IconButton(
                                onClick = {
                                    selectedMessages.forEach { messageId ->
                                        viewModel.unarchiveMessage(messageId)
                                    }
                                    isMultiSelectMode = false
                                    selectedMessages = emptySet()
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                },
                                enabled = selectedMessages.isNotEmpty()
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Unarchive,
                                    contentDescription = "Unarchive selected",
                                    tint = if (selectedMessages.isNotEmpty()) nhsBlack else nhsGrey
                                )
                            }
                        }

                        // Delete action (available in both tabs)
                        IconButton(
                            onClick = {
                                selectedMessages.forEach { messageId ->
                                    viewModel.deleteMessage(messageId)
                                }
                                isMultiSelectMode = false
                                selectedMessages = emptySet()
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                            },
                            enabled = selectedMessages.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete selected",
                                tint = if (selectedMessages.isNotEmpty()) Color.Red else nhsGrey
                            )
                        }
                    } else {
                        // Normal mode - select and filter actions
                        IconButton(onClick = {
                            isMultiSelectMode = true
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Select messages",
                                tint = nhsBlack
                            )
                        }

                        IconButton(onClick = {
                            showFilterMenu = true
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                        }) {
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
                                    filterType = "all"
                                    showFilterMenu = false
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Unread only") },
                                onClick = {
                                    filterType = "unread"
                                    showFilterMenu = false
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Flagged only") },
                                onClick = {
                                    filterType = "flagged"
                                    showFilterMenu = false
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey5
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = nhsGrey5,
                contentColor = nhsBlue
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            // Exit multi-select mode when switching tabs
                            if (isMultiSelectMode) {
                                isMultiSelectMode = false
                                selectedMessages = emptySet()
                            }
                        },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                                // Show unread count badge on Inbox tab
                                if (index == 0 && unreadCount > 0) {
                                    Surface(
                                        shape = CircleShape,
                                        color = nhsBlue,
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Text(
                                                text = unreadCount.toString(),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }

            // Messages List
            Surface(
                color = nhsGrey5,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = filteredMessages,
                        key = { it.id }
                    ) { message ->
                        SwipeableMessageItem(
                            message = message,
                            isInArchive = selectedTabIndex == 1,
                            isMultiSelectMode = isMultiSelectMode,
                            isSelected = selectedMessages.contains(message.id),
                            onClick = {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                if (isMultiSelectMode) {
                                    // Toggle selection in multi-select mode
                                    selectedMessages = if (selectedMessages.contains(message.id)) {
                                        selectedMessages - message.id
                                    } else {
                                        selectedMessages + message.id
                                    }
                                } else {
                                    // Mark as read when clicked, then navigate to detail
                                    viewModel.markAsRead(message.id)
                                    navController.navigate("message_detail/${message.id}")
                                }
                            },
                            onLongPress = {
                                // Enter multi-select mode with haptic feedback
                                if (!isMultiSelectMode) {
                                    isMultiSelectMode = true
                                    selectedMessages = setOf(message.id)
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                }
                            },
                            onDelete = {
                                viewModel.deleteMessage(message.id)
                            },
                            onMarkUnread = {
                                viewModel.markAsUnread(message.id)
                            },
                            onArchive = {
                                viewModel.archiveMessage(message.id)
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SwipeableMessageItem(
    message: Message,
    isInArchive: Boolean,
    isMultiSelectMode: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongPress: () -> Unit = {},
    onDelete: () -> Unit,
    onMarkUnread: () -> Unit,
    onArchive: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Swipe right - Mark as Unread
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onMarkUnread()
                    false // Reset to settled state (don't dismiss)
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    // Swipe left - Archive/Unarchive or Delete
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (isInArchive) {
                        onDelete()
                        true // Allow dismiss for delete
                    } else {
                        onArchive()
                        true // Allow dismiss for archive
                    }
                }
                else -> false
            }
        },
        positionalThreshold = { it * 0.5f } // Require 50% swipe to trigger
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                targetValue = when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> nhsBlue // NHS Blue for mark unread
                    SwipeToDismissBoxValue.EndToStart -> {
                        if (isInArchive) Color(0xFFD32F2F) // Solid red for delete
                        else Color(0xFF007F3B) // NHS Green for archive
                    }
                    else -> Color.Transparent
                },
                label = "swipe_background_color"
            )
            val alignment = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                else -> Alignment.Center
            }
            val icon = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.MarkEmailUnread
                SwipeToDismissBoxValue.EndToStart -> {
                    if (isInArchive) Icons.Filled.Delete else Icons.Filled.Archive
                }
                else -> Icons.Filled.Delete
            }
            val scale by animateFloatAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.75f else 1f,
                label = "icon_scale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                when (direction) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Mark as Unread",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .scale(scale)
                        )
                    }
                    SwipeToDismissBoxValue.EndToStart -> {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (isInArchive) "Delete" else "Archive",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .scale(scale)
                        )
                    }
                    else -> {}
                }
            }
        },
        enableDismissFromStartToEnd = !isMultiSelectMode,
        enableDismissFromEndToStart = !isMultiSelectMode
    ) {
        Surface(
            color = if (isSelected) nhsBlue.copy(alpha = 0.1f) else nhsGrey5,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                MessageListItem(
                    message = message,
                    isMultiSelectMode = isMultiSelectMode,
                    isSelected = isSelected,
                    onClick = onClick,
                    onLongPress = onLongPress
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 72.dp),
                    color = nhsGrey4
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageListItem(
    message: Message,
    isMultiSelectMode: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongPress: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Selection checkbox or avatar
        if (isMultiSelectMode) {
            Icon(
                imageVector = if (isSelected) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                contentDescription = if (isSelected) "Selected" else "Not selected",
                tint = if (isSelected) nhsBlue else nhsGrey,
                modifier = Modifier.size(48.dp)
            )
        } else {
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
                        text = message.sender.first().toString().uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = nhsBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.SemiBold,
                    color = nhsGrey,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 2.dp)
                )

                // Attachment indicator
                if (message.hasAttachment) {
                    Icon(
                        imageVector = Icons.Outlined.AttachFile,
                        contentDescription = "Has attachment",
                        tint = nhsGrey,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 4.dp)
                    )
                }
            }

            Text(
                text = message.preview,
                style = MaterialTheme.typography.bodySmall,
                color = nhsGrey,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Flag icon - only show when message is flagged (read-only indicator)
        if (!isMultiSelectMode && message.isFlagged) {
            Icon(
                imageVector = Icons.Filled.Flag,
                contentDescription = "Flagged message",
                tint = Color(0xFFFFA726),
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 8.dp)
            )
        }
    }
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