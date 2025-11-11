package com.prototype.nhsappdv1.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.prototype.nhsappdv1.screens.messages.Message
import com.prototype.nhsappdv1.ui.theme.nhsBlue

class MessagesViewModel : ViewModel() {

    // State for messages
    val messages = mutableStateOf(getSampleMessages())

    // Computed property for unread count (non-archived only)
    val unreadCount: Int
        get() = messages.value.count { !it.isRead && !it.isArchived }

    // Mark a message as read
    fun markAsRead(messageId: String) {
        messages.value = messages.value.map { message ->
            if (message.id == messageId) {
                message.copy(isRead = true)
            } else {
                message
            }
        }
    }

    // Mark a message as unread
    fun markAsUnread(messageId: String) {
        messages.value = messages.value.map { message ->
            if (message.id == messageId) {
                message.copy(isRead = false)
            } else {
                message
            }
        }
    }

    // Archive a message
    fun archiveMessage(messageId: String) {
        messages.value = messages.value.map { message ->
            if (message.id == messageId) {
                message.copy(isArchived = true)
            } else {
                message
            }
        }
    }

    // Unarchive a message
    fun unarchiveMessage(messageId: String) {
        messages.value = messages.value.map { message ->
            if (message.id == messageId) {
                message.copy(isArchived = false)
            } else {
                message
            }
        }
    }

    // Delete a message (remove from list)
    fun deleteMessage(messageId: String) {
        messages.value = messages.value.filter { it.id != messageId }
    }

    // Sample data
    private fun getSampleMessages(): List<Message> {
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
                lastMessage = "You can now book video consultations with your GP through the NHS App. Tap here to learn more.",
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
}