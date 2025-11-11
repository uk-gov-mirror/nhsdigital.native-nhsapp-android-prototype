package com.prototype.nhsappcv1.screens.messages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappcv1.viewmodels.MessagesViewModel
import com.prototype.nhsappcv1.ui.theme.nhsBlack
import com.prototype.nhsappcv1.ui.theme.nhsBlue
import com.prototype.nhsappcv1.ui.theme.nhsGrey
import com.prototype.nhsappcv1.ui.theme.nhsGrey4
import com.prototype.nhsappcv1.ui.theme.nhsGrey5
import kotlinx.coroutines.launch
import android.view.SoundEffectConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetail(
    navController: NavController,
    messageId: String,
    messagesViewModel: MessagesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // Observe the messages state from ViewModel - this ensures reactive updates
    val messages by messagesViewModel.messages.collectAsStateWithLifecycle()

    // Get the message from the current state
    val message = messages.find { it.id == messageId }

    // Get view for sound effects
    val view = LocalView.current

    // State for dropdown menu
    var showMenu by remember { mutableStateOf(false) }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Mark message as read when the screen is opened
    LaunchedEffect(messageId) {
        messagesViewModel.markAsRead(messageId)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            LargeTopAppBar(
                title = {
                    if (message != null) {
                        Column {
                            Text(
                                text = message.subject,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    } else {
                        Text("Message")
                    }
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
                actions = {
                    if (message != null) {
                        // Flag/Unflag button - Toggles flag state with snackbar
                        IconButton(
                            onClick = {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                val wasFlagged = message.isFlagged
                                messagesViewModel.toggleFlag(messageId)
                                scope.launch {
                                    val snackbarMessage = if (wasFlagged) {
                                        "Message unflagged"
                                    } else {
                                        "Message flagged"
                                    }
                                    val result = snackbarHostState.showSnackbar(
                                        message = snackbarMessage,
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        // Undo the flag toggle
                                        messagesViewModel.toggleFlag(messageId)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (message.isFlagged) Icons.Filled.Flag else Icons.Outlined.Flag,
                                contentDescription = if (message.isFlagged) "Unflag message" else "Flag message",
                                tint = if (message.isFlagged) Color(0xFFFFA726) else nhsBlack
                            )
                        }

                        // More options menu
                        IconButton(onClick = {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            showMenu = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More options",
                                tint = nhsBlack
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            // Archive/Unarchive option
                            DropdownMenuItem(
                                text = {
                                    Text(if (message.isArchived) "Unarchive" else "Archive")
                                },
                                onClick = {
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                    val wasArchived = message.isArchived
                                    if (wasArchived) {
                                        messagesViewModel.unarchiveMessage(messageId)
                                    } else {
                                        messagesViewModel.archiveMessage(messageId)
                                    }
                                    showMenu = false

                                    scope.launch {
                                        val snackbarMessage = if (wasArchived) {
                                            "Message unarchived"
                                        } else {
                                            "Message archived"
                                        }
                                        val result = snackbarHostState.showSnackbar(
                                            message = snackbarMessage,
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            // Undo the archive action
                                            if (wasArchived) {
                                                messagesViewModel.archiveMessage(messageId)
                                            } else {
                                                messagesViewModel.unarchiveMessage(messageId)
                                            }
                                        } else {
                                            // Only navigate back if user didn't undo
                                            navController.navigateUp()
                                        }
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Archive,
                                        contentDescription = null
                                    )
                                }
                            )

                            // Mark as unread option
                            DropdownMenuItem(
                                text = { Text("Mark as unread") },
                                onClick = {
                                    view.playSoundEffect(SoundEffectConstants.CLICK)
                                    messagesViewModel.markAsUnread(messageId)
                                    showMenu = false

                                    scope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Marked as unread",
                                            actionLabel = "Undo"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            // Undo by marking as read again
                                            messagesViewModel.markAsRead(messageId)
                                        } else {
                                            // Only navigate back if user didn't undo
                                            navController.navigateUp()
                                        }
                                    }
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
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxSize()
        ) {
            if (message != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Message header card
                    Surface(
                        color = nhsGrey5,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Sender info
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Avatar
                                Surface(
                                    modifier = Modifier
                                        .size(40.dp)
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
                                            style = MaterialTheme.typography.titleMedium,
                                            color = nhsBlue,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = message.sender,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = nhsBlack
                                    )
                                    Text(
                                        text = "to me",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = nhsGrey
                                    )
                                }

                                Text(
                                    text = message.timestamp,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = nhsGrey
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = nhsGrey4)

                    // Message body
                    Surface(
                        color = nhsGrey5,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = getFullMessageContent(message),
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5f,
                                color = nhsBlack
                            )
                        }
                    }

                    // Attachments section
                    if (message.hasAttachment) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            color = nhsGrey5,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Attachments",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = nhsBlack,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                // Attachment item
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { /* Handle attachment click */ },
                                    color = Color.White,
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Surface(
                                            modifier = Modifier.size(40.dp),
                                            color = nhsBlue.copy(alpha = 0.1f),
                                            shape = MaterialTheme.shapes.small
                                        ) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.AttachFile,
                                                    contentDescription = null,
                                                    tint = nhsBlue,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Medical_Records.pdf",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Medium,
                                                color = nhsBlack
                                            )
                                            Text(
                                                text = "245 KB",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = nhsGrey
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Message not found
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Message not found",
                        style = MaterialTheme.typography.titleLarge,
                        color = nhsGrey
                    )
                }
            }
        }
    }
}

// Helper function to get full message content
fun getFullMessageContent(message: Message): String {
    return when (message.id) {
        "1" -> """
            Dear Patient,
            
            Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department at Leeds Teaching Hospitals NHS Trust.
            
            Please arrive 15 minutes early to allow time for check-in. Bring your NHS number and any relevant medical documents.
            
            Location:
            Cardiology Department
            Leeds General Infirmary
            Great George Street
            Leeds LS1 3EX
            
            If you need to cancel or reschedule, please contact us at least 24 hours in advance on 0113 243 2799.
            
            We look forward to seeing you.
            
            Best regards,
            NHS Leeds Teaching Hospitals
        """.trimIndent()

        "2" -> """
            Dear Patient,
            
            Your recent blood test results are now available to view in your NHS account.
            
            Test Date: 1st November 2025
            Test Type: Full Blood Count, Lipid Profile
            
            All results are within normal ranges. Your cholesterol levels have improved since your last test, which is excellent news.
            
            If you have any questions about your results, please don't hesitate to contact your GP surgery.
            
            Keep up the good work with your healthy lifestyle changes!
            
            Dr Sarah Johnson
            Oak Tree Surgery
        """.trimIndent()

        "3" -> """
            Dear Patient,
            
            Your repeat prescription is ready for collection at the pharmacy.
            
            Prescription Details:
            - Medication: As requested
            - Quantity: 28 days supply
            - Collection: Boots Pharmacy, High Street
            
            Please collect within 7 days. You will need to bring photo ID.
            
            If you have any questions, please contact the surgery on 0113 456 7890.
            
            Oak Tree Surgery
        """.trimIndent()

        "4" -> """
            Hello,
            
            We're excited to announce a new feature in the NHS App: Video Consultations!
            
            You can now book and attend GP appointments from the comfort of your home using video calls.
            
            Benefits:
            • No travel time needed
            • Flexible appointment times
            • Safe and secure NHS-approved platform
            • Perfect for follow-ups and routine check-ups
            
            To get started, go to the Appointments section and look for the "Video Consultation" option.
            
            If you have any questions, visit our help section or contact your GP surgery.
            
            The NHS App Team
        """.trimIndent()

        "5" -> """
            Dear Patient,
            
            Following your recent consultation with our specialist, we would like to schedule a follow-up appointment to review your progress.
            
            Please contact our appointments team on 0161 276 1234 (option 2) to book your appointment at your earliest convenience.
            
            When calling, please have your hospital number ready: MRI 123456789
            
            We recommend scheduling your follow-up within the next 4-6 weeks.
            
            Manchester Royal Infirmary
            Outpatient Department
        """.trimIndent()

        "6" -> """
            Dear Patient,
            
            Winter is approaching, and it's time to protect yourself against flu.
            
            The flu vaccine is free for:
            • People aged 65 and over
            • Pregnant women
            • Those with certain medical conditions
            • Frontline health and social care workers
            
            You can book your appointment at:
            • Your GP surgery
            • Local pharmacy
            • Community vaccination centres
            
            Protect yourself and those around you this winter. Book your appointment today!
            
            NHS Vaccination Centre
        """.trimIndent()

        "7" -> """
            Dear Patient,
            
            Your annual medication review is now due. This is an important check-up to ensure your medications are still appropriate and working well for you.
            
            During the review, we will:
            • Discuss your current medications
            • Check for any side effects
            • Review your overall health
            • Make any necessary adjustments
            
            Please book a medication review appointment with reception at your convenience. This can be done in person or via telephone.
            
            Call: 0113 234 5678
            
            Dr Michael Chen
            Oak Tree Surgery
        """.trimIndent()

        "8" -> """
            Dear User,
            
            Thank you for using NHS 111 online for your recent health query.
            
            Based on the symptoms you described, we recommend that you contact your GP surgery to arrange an appointment within the next few days.
            
            In the meantime:
            • Continue to monitor your symptoms
            • Rest and stay hydrated
            • Take over-the-counter pain relief if needed
            
            If your symptoms worsen or you develop new symptoms, please use NHS 111 again or call 111.
            
            For emergencies, always call 999.
            
            Reference Number: 111-2025-456789
            
            NHS 111
        """.trimIndent()

        else -> message.preview
    }
}

@Preview(showSystemUi = true)
@Composable
fun MessageDetailPreview() {
    MessageDetail(
        navController = rememberNavController(),
        messageId = "1"
    )
}