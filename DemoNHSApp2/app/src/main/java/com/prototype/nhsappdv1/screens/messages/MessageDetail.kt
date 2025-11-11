package com.prototype.nhsappdv1.screens.messages

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey
import com.prototype.nhsappdv1.ui.theme.nhsGrey4
import com.prototype.nhsappdv1.ui.theme.nhsGrey5

data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: String,
    val isFromUser: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetail(
    navController: NavController,
    messageId: String,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current

    // Find the conversation by ID
    val conversation = getSampleMessages().find { it.id == messageId }
    val listState = rememberLazyListState()

    // Get chat messages for this conversation
    val chatMessages = getConversationMessages(messageId)

    // Auto-scroll to bottom when messages load
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (conversation != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Avatar
                            Surface(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                color = conversation.avatarColor.copy(alpha = 0.15f)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = conversation.sender.split(" ")
                                            .mapNotNull { it.firstOrNull() }
                                            .take(2)
                                            .joinToString("")
                                            .uppercase(),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = conversation.avatarColor,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = conversation.sender,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    } else {
                        Text("Conversation")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        view.playSoundEffect(android.view.SoundEffectConstants.CLICK)
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        view.playSoundEffect(android.view.SoundEffectConstants.CLICK)
                        /* TODO: Implement voice call */
                    }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Voice call",
                            tint = nhsBlack
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = nhsGrey5
                )
            )
        }
    ) { paddingValues ->
        Surface(
            color = nhsGrey5,
            modifier = Modifier.fillMaxSize()
        ) {
            if (conversation != null) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Add some top padding
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Date header
                    item {
                        DateHeader(text = "Today")
                    }

                    // Chat messages
                    items(chatMessages) { message ->
                        ChatBubble(
                            message = message,
                            avatarColor = conversation.avatarColor,
                            senderInitials = conversation.sender.split(" ")
                                .mapNotNull { it.firstOrNull() }
                                .take(2)
                                .joinToString("")
                                .uppercase()
                        )
                    }

                    // Add some bottom padding
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                // Conversation not found
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Conversation not found",
                        style = MaterialTheme.typography.titleLarge,
                        color = nhsGrey
                    )
                }
            }
        }
    }
}

@Composable
fun DateHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = nhsGrey4.copy(alpha = 0.5f)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = nhsGrey,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    avatarColor: Color,
    senderInitials: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromUser) {
            // Show avatar for received messages
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .align(Alignment.Bottom),
                color = avatarColor.copy(alpha = 0.15f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = senderInitials,
                        style = MaterialTheme.typography.labelSmall,
                        color = avatarColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = if (message.isFromUser) 20.dp else 4.dp,
                    topEnd = if (message.isFromUser) 4.dp else 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                color = if (message.isFromUser) nhsBlue else Color.White,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isFromUser) Color.White else nhsBlack,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }

            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = nhsGrey,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

// Helper function to get conversation messages
fun getConversationMessages(conversationId: String): List<ChatMessage> {
    return when (conversationId) {
        "1" -> listOf(
            ChatMessage(
                id = "1-1",
                text = "Hello, we'd like to confirm your appointment at our Cardiology Department.",
                timestamp = "9:15 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "1-2",
                text = "Your appointment is scheduled for 15th November at 10:00 AM.",
                timestamp = "9:15 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "1-3",
                text = "Please arrive 15 minutes early and bring your NHS number and any relevant medical documents.",
                timestamp = "9:16 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "1-4",
                text = "Location: Cardiology Department, Leeds General Infirmary, Great George Street, Leeds LS1 3EX",
                timestamp = "9:16 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "1-5",
                text = "If you need to cancel or reschedule, please contact us at least 24 hours in advance on 0113 243 2799.",
                timestamp = "9:17 AM",
                isFromUser = false
            )
        )

        "2" -> listOf(
            ChatMessage(
                id = "2-1",
                text = "Hi! Your recent blood test results are now available.",
                timestamp = "Yesterday, 2:30 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "2-2",
                text = "Test Date: 1st November 2025\nTest Type: Full Blood Count, Lipid Profile",
                timestamp = "Yesterday, 2:30 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "2-3",
                text = "All results are within normal ranges. Your cholesterol levels have improved since your last test! 🎉",
                timestamp = "Yesterday, 2:31 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "2-4",
                text = "That's great news! Thank you for letting me know.",
                timestamp = "Yesterday, 2:45 PM",
                isFromUser = true
            ),
            ChatMessage(
                id = "2-5",
                text = "Keep up the good work with your healthy lifestyle changes! If you have any questions, feel free to contact the surgery.",
                timestamp = "Yesterday, 2:46 PM",
                isFromUser = false
            )
        )

        "3" -> listOf(
            ChatMessage(
                id = "3-1",
                text = "Your repeat prescription is ready for collection!",
                timestamp = "2 days ago, 11:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "3-2",
                text = "Prescription Details:\n• Medication: As requested\n• Quantity: 28 days supply\n• Collection: Boots Pharmacy, High Street",
                timestamp = "2 days ago, 11:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "3-3",
                text = "Please collect within 7 days. You will need to bring photo ID.",
                timestamp = "2 days ago, 11:01 AM",
                isFromUser = false
            )
        )

        "4" -> listOf(
            ChatMessage(
                id = "4-1",
                text = "🎉 Important Update from NHS App Team",
                timestamp = "Nov 1, 10:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-2",
                text = "Hello! We're excited to announce several major updates to the NHS App that will significantly enhance your healthcare experience and make managing your health easier than ever before.",
                timestamp = "Nov 1, 10:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-3",
                text = "📹 VIDEO CONSULTATIONS\n\nYou can now book and attend GP appointments from the comfort of your home through our new integrated video consultation service. This feature has been designed with your convenience and safety in mind.\n\nKey Benefits:\n• No travel time or waiting rooms needed\n• Flexible appointment times including early morning and evening slots\n• Safe and secure NHS-approved platform with end-to-end encryption\n• Perfect for follow-ups, routine check-ups, and non-emergency consultations\n• Option to share medical images or documents during the call\n• Automatic appointment reminders sent to your device\n\nTo get started, simply go to the Appointments section in the app and look for the 'Video Consultation' option when booking your next appointment.",
                timestamp = "Nov 1, 10:01 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-4",
                text = "📅 ENHANCED APPOINTMENT BOOKING\n\nWe've completely redesigned our appointment booking system based on your feedback. The new system is faster, more intuitive, and gives you greater control over your healthcare schedule.\n\nNew Features Include:\n• View real-time availability across multiple GP surgeries in your area\n• Book appointments up to 8 weeks in advance\n• Easy rescheduling without having to call your surgery\n• Ability to specify appointment preferences (specific GP, time of day, appointment type)\n• Automatic calendar integration with reminders\n• Same-day emergency appointment requests\n• Clear information about appointment types and what to expect\n\nThe system now supports booking for various services including general consultations, health checks, vaccinations, blood tests, and specialist nurse appointments.",
                timestamp = "Nov 1, 10:02 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-5",
                text = "💊 PRESCRIPTION TRACKING & MANAGEMENT\n\nManaging your medications just got significantly easier with our new comprehensive prescription management system.\n\nWhat's New:\n• Real-time tracking of your prescription from surgery to pharmacy\n• Push notifications when prescriptions are ready for collection\n• View complete medication history including dosage changes\n• Set up automatic repeat prescription requests (never run out again!)\n• Medication reminders to help you take medications on time\n• Information about your medications including side effects and interactions\n• Option to nominate your preferred pharmacy for collections\n• Direct messaging with pharmacy for queries\n• Ability to request prescription delivery for eligible patients\n\nThis feature integrates seamlessly with your GP surgery and local pharmacies to ensure you always have the medications you need when you need them.",
                timestamp = "Nov 1, 10:03 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-6",
                text = "📋 COMPLETE HEALTH RECORDS ACCESS\n\nFor the first time, you now have secure access to your complete NHS health records directly through the app. This empowers you to take control of your healthcare journey.\n\nAccess Includes:\n• Full medical history including diagnoses and treatments\n• All test results (blood tests, x-rays, scans) with easy-to-understand explanations\n• Vaccination records and immunisation history\n• Allergy and adverse reaction information\n• Current and past medications\n• Hospital admission records\n• Referral letters and specialist reports\n• Care plans and treatment pathways\n• Ability to add your own health notes and observations\n• Export and share records with other healthcare providers\n\nAll information is presented in clear, accessible language with medical terms explained in plain English. You maintain complete control over who can access your information.",
                timestamp = "Nov 1, 10:04 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-7",
                text = "🏥 HOSPITAL SERVICES INTEGRATION\n\nWe've expanded beyond primary care to include comprehensive hospital services management.\n\nNew Hospital Features:\n• View upcoming hospital appointments and procedures\n• Access pre-operative instructions and preparation guides\n• Check waiting times for A&E departments near you\n• Track referral status from GP to specialist\n• Receive updates on surgery dates and scheduling\n• Access discharge summaries and post-operative care instructions\n• Virtual hospital tours for nervous patients\n• Direct messaging with hospital departments\n• Find your way with interactive hospital maps\n\nThis integration means all your healthcare - from GP visits to hospital care - is now accessible in one place.",
                timestamp = "Nov 1, 10:05 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-8",
                text = "🔔 INTELLIGENT HEALTH NOTIFICATIONS\n\nOur new smart notification system keeps you informed about important health matters without overwhelming you with alerts.\n\nNotification Types:\n• Upcoming appointments with customizable reminders\n• Test results available to view\n• Prescriptions ready for collection\n• Vaccination and screening recommendations based on your age and health\n• Health campaigns relevant to you\n• Medication refill reminders\n• Follow-up appointment suggestions\n• Important health alerts from your surgery\n\nYou have complete control over which notifications you receive and how you receive them (push, email, or SMS).",
                timestamp = "Nov 1, 10:06 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-9",
                text = "👨‍👩‍👧‍👦 FAMILY ACCOUNT MANAGEMENT\n\nParents and carers can now manage healthcare for their entire family from a single app.\n\nFamily Features:\n• Add multiple family members under your account\n• Book appointments for children and dependents\n• View health records for those in your care (with appropriate permissions)\n• Manage prescriptions for the whole family\n• Receive consolidated health updates\n• Set up different notification preferences for each family member\n• Proxy access for elderly relatives\n• Secure linking with verifiable permissions\n\nThis makes coordinating family healthcare appointments and medications much simpler, especially for busy parents managing multiple children's health needs.",
                timestamp = "Nov 1, 10:07 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-10",
                text = "🔐 ENHANCED SECURITY & PRIVACY\n\nYour health data security is our top priority. We've implemented additional security measures:\n\n• Biometric authentication (Face ID/Fingerprint)\n• Two-factor authentication option\n• Automatic session timeout for added security\n• Encrypted data storage and transmission\n• Activity log showing who accessed your records and when\n• Granular privacy controls\n• GDPR compliant with right to data deletion\n• Regular security audits and penetration testing\n• Compliance with NHS Digital security standards\n\nYou can review and manage all security settings in the app's Security Centre.",
                timestamp = "Nov 1, 10:08 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-11",
                text = "📊 PERSONAL HEALTH DASHBOARD\n\nTrack your health journey with our new comprehensive dashboard featuring:\n\n• Health trends and insights over time\n• Upcoming appointments and tasks at a glance\n• Medication adherence tracking\n• Health goals and progress monitoring\n• Integration with fitness apps and wearables\n• Symptom diary and health journal\n• Customizable health metrics display\n• Shareable health summaries for appointments\n\nThe dashboard uses intuitive charts and visualizations to help you understand your health data better and make informed decisions about your care.",
                timestamp = "Nov 1, 10:09 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-12",
                text = "♿ ACCESSIBILITY IMPROVEMENTS\n\nWe've made the app more accessible for everyone:\n\n• Full screen reader support with optimized navigation\n• Adjustable text sizes up to 200%\n• High contrast mode for better visibility\n• Voice commands for hands-free operation\n• Simplified language option for all content\n• Multilingual support (20+ languages available)\n• Video content with captions and transcripts\n• Clear visual indicators and status updates\n• Compatibility with assistive technologies\n\nOur goal is to ensure everyone can manage their healthcare independently, regardless of any accessibility needs.",
                timestamp = "Nov 1, 10:10 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-13",
                text = "📱 HOW TO UPDATE\n\nTo access all these new features:\n\n1. Open your device's app store (App Store for iOS, Google Play for Android)\n2. Search for 'NHS App'\n3. Tap 'Update' if available\n4. Once updated, open the app and you'll see a welcome tour of the new features\n5. Review your preferences and notification settings\n6. Explore the new features at your own pace\n\nThe update is rolling out to all users over the next 48 hours. If you don't see it immediately, please check back shortly.\n\nMinimum requirements:\n• iOS 14.0 or later for Apple devices\n• Android 8.0 or later for Android devices\n• Active internet connection for initial setup",
                timestamp = "Nov 1, 10:11 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-14",
                text = "❓ HELP & SUPPORT\n\nWe're here to help you make the most of these new features:\n\n• In-app help guides and video tutorials\n• 24/7 technical support via the Help section\n• Live chat support Monday-Friday 8am-6pm\n• Email support: support@nhs-app.nhs.uk\n• Phone support: 0800 111 4477 (free from UK landlines and mobiles)\n• FAQ section with answers to common questions\n• Community forum to connect with other users\n\nIf you experience any issues or have suggestions for improvements, please don't hesitate to contact us. Your feedback helps us make the app better for everyone.",
                timestamp = "Nov 1, 10:12 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "4-15",
                text = "Thank you for using the NHS App. We're committed to continuously improving your digital healthcare experience. More exciting features are planned for release in the coming months!\n\nStay healthy,\nThe NHS App Team 💙",
                timestamp = "Nov 1, 10:13 AM",
                isFromUser = false
            )
        )

        "5" -> listOf(
            ChatMessage(
                id = "5-1",
                text = "Hello! Following your recent consultation, we'd like to schedule a follow-up appointment.",
                timestamp = "Oct 28, 3:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "5-2",
                text = "Please contact our appointments team on 0161 276 1234 (option 2) to book at your earliest convenience.",
                timestamp = "Oct 28, 3:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "5-3",
                text = "Your hospital number: MRI 123456789\n\nWe recommend scheduling within the next 4-6 weeks.",
                timestamp = "Oct 28, 3:01 PM",
                isFromUser = false
            )
        )

        "6" -> listOf(
            ChatMessage(
                id = "6-1",
                text = "Winter is approaching! It's time to protect yourself against flu. 🍂",
                timestamp = "Oct 25, 9:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "6-2",
                text = "The flu vaccine is free for:\n• People aged 65 and over\n• Pregnant women\n• Those with certain medical conditions\n• Frontline health workers",
                timestamp = "Oct 25, 9:00 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "6-3",
                text = "You can book at your GP surgery, local pharmacy, or community vaccination centres.",
                timestamp = "Oct 25, 9:01 AM",
                isFromUser = false
            ),
            ChatMessage(
                id = "6-4",
                text = "Protect yourself and those around you this winter!",
                timestamp = "Oct 25, 9:01 AM",
                isFromUser = false
            )
        )

        "7" -> listOf(
            ChatMessage(
                id = "7-1",
                text = "Your annual medication review is now due.",
                timestamp = "Oct 20, 2:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "7-2",
                text = "During the review, we will:\n• Discuss your current medications\n• Check for any side effects\n• Review your overall health\n• Make any necessary adjustments",
                timestamp = "Oct 20, 2:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "7-3",
                text = "Please book an appointment with reception at your convenience. This can be in person or via telephone.",
                timestamp = "Oct 20, 2:01 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "7-4",
                text = "Call: 0113 234 5678",
                timestamp = "Oct 20, 2:01 PM",
                isFromUser = false
            )
        )

        "8" -> listOf(
            ChatMessage(
                id = "8-1",
                text = "Thank you for using NHS 111 online for your recent health query.",
                timestamp = "Oct 15, 1:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "8-2",
                text = "Based on your symptoms, we recommend contacting your GP surgery to arrange an appointment within the next few days.",
                timestamp = "Oct 15, 1:00 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "8-3",
                text = "In the meantime:\n• Monitor your symptoms\n• Rest and stay hydrated\n• Take over-the-counter pain relief if needed",
                timestamp = "Oct 15, 1:01 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "8-4",
                text = "If your symptoms worsen, please use NHS 111 again or call 111. For emergencies, always call 999.",
                timestamp = "Oct 15, 1:01 PM",
                isFromUser = false
            ),
            ChatMessage(
                id = "8-5",
                text = "Reference Number: 111-2025-456789",
                timestamp = "Oct 15, 1:02 PM",
                isFromUser = false
            )
        )

        else -> emptyList()
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

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ChatBubblePreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChatBubble(
            message = ChatMessage(
                id = "1",
                text = "Your appointment has been confirmed for 15th November at 10:00 AM.",
                timestamp = "9:30 AM",
                isFromUser = false
            ),
            avatarColor = Color(0xFF0072CE),
            senderInitials = "NH"
        )

        ChatBubble(
            message = ChatMessage(
                id = "2",
                text = "Thank you for the confirmation!",
                timestamp = "9:35 AM",
                isFromUser = true
            ),
            avatarColor = Color(0xFF0072CE),
            senderInitials = "NH"
        )
    }
}