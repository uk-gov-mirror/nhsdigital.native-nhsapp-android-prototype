package com.prototype.nhsappdv1.screens.messages

import android.view.SoundEffectConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MarkEmailUnread
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.viewmodels.MessagesViewModel
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey
import com.prototype.nhsappdv1.ui.theme.nhsGrey5
import com.prototype.nhsappdv1.ui.theme.nhsRed

data class Message(
    val id: String,
    val sender: String,
    val lastMessage: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val isArchived: Boolean = false,
    val avatarColor: Color = nhsBlue
)

@Composable
fun AnimatedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    // Animate width expansion
    val width by animateDpAsState(
        targetValue = if (isExpanded) 280.dp else 48.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "width"
    )

    // Animate corner radius
    val cornerRadius by animateDpAsState(
        targetValue = if (isExpanded) 24.dp else 24.dp,
        animationSpec = tween(300),
        label = "cornerRadius"
    )

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            focusRequester.requestFocus()
        }
    }

    Box(
        modifier = modifier
            .width(width)
            .clip(CircleShape)
            .background(nhsGrey5.copy(alpha = 0.5f))
    ) {
        Crossfade(
            targetState = isExpanded,
            animationSpec = tween(300),
            label = "searchCrossfade"
        ) { expanded ->
            if (expanded) {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = "Search messages...",
                            color = nhsGrey,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = nhsGrey,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = onClearClick,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search",
                                    tint = nhsGrey,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = CircleShape,
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = nhsBlue,
                        focusedTextColor = nhsBlack,
                        unfocusedTextColor = nhsBlack
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                )
            } else {
                // Just the search icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = nhsBlack,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Messages(
    navController: NavController,
    viewModel: MessagesViewModel,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Multi-select state
    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedMessages by remember { mutableStateOf(setOf<String>()) }

    // Search state
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Get non-archived messages from ViewModel
    val allMessages = viewModel.messages.value.filter { !it.isArchived }

    // Filter messages based on search query
    val messages = if (searchQuery.isNotBlank()) {
        allMessages.filter { message ->
            message.sender.contains(searchQuery, ignoreCase = true) ||
                    message.lastMessage.contains(searchQuery, ignoreCase = true)
        }
    } else {
        allMessages
    }

    val unreadCount = allMessages.count { !it.isRead }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isSelectionMode) "${selectedMessages.size} selected" else "Messages",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                        if (!isSelectionMode && !isSearchActive && unreadCount > 0) {
                            Text(
                                text = "$unreadCount unread",
                                style = MaterialTheme.typography.bodySmall,
                                color = nhsBlue,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (isSelectionMode) {
                        IconButton(onClick = {
                            isSelectionMode = false
                            selectedMessages = setOf()
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Exit selection mode",
                                tint = nhsBlack
                            )
                        }
                    } else if (isSearchActive) {
                        IconButton(onClick = {
                            isSearchActive = false
                            searchQuery = ""
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close search",
                                tint = nhsBlack
                            )
                        }
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        if (!isSelectionMode) {
                            // Animated search that morphs from icon to input
                            Box(
                                modifier = Modifier.clickable(
                                    enabled = !isSearchActive,
                                    indication = null,
                                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                                ) {
                                    if (!isSearchActive) {
                                        isSearchActive = true
                                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    }
                                }
                            ) {
                                AnimatedSearchBar(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it },
                                    onClearClick = {
                                        searchQuery = ""
                                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    },
                                    isExpanded = isSearchActive
                                )
                            }

                            if (!isSearchActive) {
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(onClick = {
                                    navController.navigate("archived_messages")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Archive,
                                        contentDescription = "View archived messages",
                                        tint = nhsBlack
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = nhsGrey5,
                    scrolledContainerColor = nhsGrey5
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isSelectionMode && selectedMessages.isNotEmpty(),
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                BottomAppBar(
                    containerColor = nhsGrey5,
                    actions = {
                        // Mark as Unread button
                        IconButton(
                            onClick = {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedMessages.forEach { messageId ->
                                    viewModel.markAsUnread(messageId)
                                }
                                isSelectionMode = false
                                selectedMessages = setOf()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.MarkEmailUnread,
                                    contentDescription = "Mark as unread",
                                    tint = nhsBlue
                                )
                                Text(
                                    text = "Unread",
                                    fontSize = 12.sp,
                                    color = nhsBlue
                                )
                            }
                        }

                        // Archive button
                        IconButton(
                            onClick = {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedMessages.forEach { messageId ->
                                    viewModel.archiveMessage(messageId)
                                }
                                isSelectionMode = false
                                selectedMessages = setOf()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Archive,
                                    contentDescription = "Archive",
                                    tint = nhsRed
                                )
                                Text(
                                    text = "Archive",
                                    fontSize = 12.sp,
                                    color = nhsRed
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isSearchActive && messages.isEmpty() && searchQuery.isNotBlank()) {
                // Empty search results
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No messages found",
                            style = MaterialTheme.typography.titleMedium,
                            color = nhsGrey,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "Try a different search term",
                            style = MaterialTheme.typography.bodyMedium,
                            color = nhsGrey
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        items = messages,
                        key = { it.id }
                    ) { message ->
                        if (isSelectionMode) {
                            SelectableConversationItem(
                                message = message,
                                isSelected = selectedMessages.contains(message.id),
                                onClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    selectedMessages = if (selectedMessages.contains(message.id)) {
                                        selectedMessages - message.id
                                    } else {
                                        selectedMessages + message.id
                                    }
                                    // Exit selection mode if no items selected
                                    if (selectedMessages.isEmpty()) {
                                        isSelectionMode = false
                                    }
                                },
                                searchQuery = searchQuery
                            )
                        } else {
                            SwipeableConversationItem(
                                message = message,
                                onClick = {
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                    viewModel.markAsRead(message.id)
                                    navController.navigate("message_detail/${message.id}")
                                },
                                onLongClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    isSelectionMode = true
                                    selectedMessages = setOf(message.id)
                                },
                                onArchive = {
                                    viewModel.archiveMessage(message.id)
                                },
                                onMarkUnread = {
                                    viewModel.markAsUnread(message.id)
                                },
                                searchQuery = searchQuery
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SwipeableConversationItem(
    message: Message,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onArchive: () -> Unit,
    onMarkUnread: () -> Unit,
    searchQuery: String = "",
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    // Left swipe - Archive
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onArchive()
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Right swipe - Mark as unread
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onMarkUnread()
                    false // Don't dismiss, just mark unread
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> nhsRed
                    SwipeToDismissBoxValue.StartToEnd -> nhsBlue
                    else -> Color.Transparent
                },
                label = "background color"
            )

            val scale by animateFloatAsState(
                targetValue = if (dismissState.targetValue != SwipeToDismissBoxValue.Settled) 1.2f else 0.8f,
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
                        Icon(
                            imageVector = Icons.Default.Archive,
                            contentDescription = "Archive",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .scale(scale)
                                .size(28.dp)
                        )
                    }
                    SwipeToDismissBoxValue.StartToEnd -> {
                        Icon(
                            imageVector = Icons.Default.MarkEmailUnread,
                            contentDescription = "Mark as unread",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .scale(scale)
                                .size(28.dp)
                        )
                    }
                    else -> {}
                }
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true
    ) {
        ConversationListItem(
            message = message,
            onClick = onClick,
            onLongClick = onLongClick,
            searchQuery = searchQuery
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectableConversationItem(
    message: Message,
    isSelected: Boolean,
    onClick: () -> Unit,
    searchQuery: String = "",
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isSelected) nhsBlue.copy(alpha = 0.1f) else nhsGrey5,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Selection indicator
            Icon(
                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = if (isSelected) "Selected" else "Not selected",
                tint = if (isSelected) nhsBlue else nhsGrey,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Avatar circle with initials
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                color = message.avatarColor.copy(alpha = 0.15f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message.sender.split(" ").mapNotNull { it.firstOrNull() }
                            .take(2)
                            .joinToString("")
                            .uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = message.avatarColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Message content
            Column(
                modifier = Modifier.weight(1f)
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
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (message.isRead) nhsGrey else nhsBlue,
                        fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.padding(top = 4.dp))

                Text(
                    text = message.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isRead) nhsGrey else nhsBlack,
                    fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConversationListItem(
    message: Message,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    searchQuery: String = "",
    modifier: Modifier = Modifier
) {
    Surface(
        color = nhsGrey5,
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle with initials
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                color = message.avatarColor.copy(alpha = 0.15f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message.sender.split(" ").mapNotNull { it.firstOrNull() }
                            .take(2)
                            .joinToString("")
                            .uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = message.avatarColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Message content
            Column(
                modifier = Modifier.weight(1f)
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
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (message.isRead) nhsGrey else nhsBlue,
                        fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.padding(top = 4.dp))

                Text(
                    text = message.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isRead) nhsGrey else nhsBlack,
                    fontWeight = if (message.isRead) FontWeight.Normal else FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

fun getSampleMessages(): List<Message> {
    return listOf(
        Message(
            id = "1",
            sender = "NHS Leeds Teaching Hospitals",
            lastMessage = "Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department.",
            timestamp = "9:30 AM",
            isRead = false,
            isArchived = false,
            avatarColor = Color(0xFF0072CE)
        ),
        Message(
            id = "2",
            sender = "Dr Sarah Johnson",
            lastMessage = "Your recent blood test results are now available to view. Please log in to your account to see the details.",
            timestamp = "Yesterday",
            isRead = false,
            isArchived = false,
            avatarColor = Color(0xFF7C4DFF)
        ),
        Message(
            id = "3",
            sender = "Oak Tree Surgery",
            lastMessage = "Your repeat prescription is ready for collection at the pharmacy. Please collect within 7 days.",
            timestamp = "2 days ago",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFF00897B)
        ),
        Message(
            id = "4",
            sender = "NHS App Team",
            lastMessage = "Important Update: New Features Available - We're excited to announce several new features including Video Consultations, Enhanced Appointment Booking, Prescription Tracking, Health Records Access, and more. These updates will help you manage your healthcare more effectively...",
            timestamp = "Nov 1",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFFE91E63)
        ),
        Message(
            id = "5",
            sender = "Manchester Royal Infirmary",
            lastMessage = "Please contact us to schedule your follow-up appointment following your recent consultation.",
            timestamp = "Oct 28",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFFFF6F00)
        ),
        Message(
            id = "6",
            sender = "Vaccination Centre",
            lastMessage = "It's that time of year again. Book your flu vaccination appointment at your local pharmacy.",
            timestamp = "Oct 25",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFF43A047)
        ),
        Message(
            id = "7",
            sender = "Dr Michael Chen",
            lastMessage = "Your annual medication review is due. Please book an appointment with reception at your convenience.",
            timestamp = "Oct 20",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFF5E35B1)
        ),
        Message(
            id = "8",
            sender = "NHS 111",
            lastMessage = "Thank you for using NHS 111 online. Based on your symptoms, we recommend you contact your GP surgery.",
            timestamp = "Oct 15",
            isRead = true,
            isArchived = false,
            avatarColor = Color(0xFFD32F2F)
        )
    )
}

@Preview(showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun MessagesPreview() {
    Messages(rememberNavController(), MessagesViewModel(), modifier = Modifier)
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ConversationListItemPreview() {
    Column {
        ConversationListItem(
            message = Message(
                id = "1",
                sender = "NHS Leeds Teaching Hospitals",
                lastMessage = "Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department.",
                timestamp = "9:30 AM",
                isRead = false,
                isArchived = false,
                avatarColor = Color(0xFF0072CE)
            ),
            onClick = {}
        )
        ConversationListItem(
            message = Message(
                id = "2",
                sender = "Dr Sarah Johnson",
                lastMessage = "Your recent blood test results are now available.",
                timestamp = "Yesterday",
                isRead = true,
                isArchived = false,
                avatarColor = Color(0xFF7C4DFF)
            ),
            onClick = {}
        )
    }
}