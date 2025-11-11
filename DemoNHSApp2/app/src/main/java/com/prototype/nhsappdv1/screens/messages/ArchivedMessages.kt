package com.prototype.nhsappdv1.screens.messages

import android.view.SoundEffectConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsGrey
import com.prototype.nhsappdv1.ui.theme.nhsGrey5
import com.prototype.nhsappdv1.ui.theme.nhsGreen
import com.prototype.nhsappdv1.viewmodels.MessagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedMessages(
    navController: NavController,
    viewModel: MessagesViewModel,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // Get archived messages from ViewModel
    val archivedMessages = viewModel.messages.value.filter { it.isArchived }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Archived",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = nhsBlack
                        )
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
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxSize()
        ) {
            if (archivedMessages.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No archived messages",
                        color = nhsGrey,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        items = archivedMessages,
                        key = { it.id }
                    ) { message ->
                        SwipeableArchivedItem(
                            message = message,
                            onClick = {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                viewModel.markAsRead(message.id)
                                navController.navigate("message_detail/${message.id}")
                            },
                            onUnarchive = {
                                viewModel.unarchiveMessage(message.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableArchivedItem(
    message: Message,
    onClick: () -> Unit,
    onUnarchive: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Right swipe - Unarchive
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onUnarchive()
                    true
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
                    SwipeToDismissBoxValue.StartToEnd -> nhsGreen // NHS Green for unarchive
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
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(
                        imageVector = Icons.Default.Unarchive,
                        contentDescription = "Unarchive",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .scale(scale)
                            .size(28.dp)
                    )
                }
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false
    ) {
        ConversationListItem(
            message = message,
            onClick = onClick
        )
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ArchivedMessagesPreview() {
    ArchivedMessages(rememberNavController(), MessagesViewModel(), modifier = Modifier)
}