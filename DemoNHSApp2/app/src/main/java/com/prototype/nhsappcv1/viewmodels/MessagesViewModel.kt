package com.prototype.nhsappcv1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prototype.nhsappcv1.screens.messages.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MessagesViewModel : ViewModel() {

    // Private mutable state - using MutableStateFlow for reactive updates
    private val _messages = MutableStateFlow(generateSampleMessages())

    // Public immutable state - this is what the UI will observe
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    // Derived state for unread count - automatically updates when messages change
    val unreadCount: StateFlow<Int> = _messages.map { messagesList ->
        messagesList.count { !it.isRead && !it.isArchived }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /**
     * Get a message by its ID
     * Note: For reactive UI updates, prefer observing messages StateFlow
     * and using find() on the collected list
     */
    fun getMessageById(messageId: String): Message? {
        return _messages.value.find { it.id == messageId }
    }

    /**
     * Toggle the flag status of a message
     */
    fun toggleFlag(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.map { message ->
                if (message.id == messageId) {
                    message.copy(isFlagged = !message.isFlagged)
                } else {
                    message
                }
            }
        }
    }

    /**
     * Archive a message
     */
    fun archiveMessage(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.map { message ->
                if (message.id == messageId) {
                    message.copy(isArchived = true)
                } else {
                    message
                }
            }
        }
    }

    /**
     * Unarchive a message
     */
    fun unarchiveMessage(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.map { message ->
                if (message.id == messageId) {
                    message.copy(isArchived = false)
                } else {
                    message
                }
            }
        }
    }

    /**
     * Mark a message as read
     */
    fun markAsRead(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.map { message ->
                if (message.id == messageId) {
                    message.copy(isRead = true)
                } else {
                    message
                }
            }
        }
    }

    /**
     * Mark a message as unread
     */
    fun markAsUnread(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.map { message ->
                if (message.id == messageId) {
                    message.copy(isRead = false)
                } else {
                    message
                }
            }
        }
    }

    /**
     * Delete a message
     */
    fun deleteMessage(messageId: String) {
        _messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }

    /**
     * Get filtered messages based on current tab
     */
    fun getFilteredMessages(showArchived: Boolean = false): List<Message> {
        return if (showArchived) {
            _messages.value.filter { it.isArchived }
        } else {
            _messages.value.filterNot { it.isArchived }
        }
    }

    /**
     * Generate sample messages for testing
     */
    private fun generateSampleMessages(): List<Message> {
        return listOf(
            Message(
                id = "1",
                sender = "NHS Leeds Teaching Hospitals",
                subject = "Appointment Confirmation - Cardiology Department",
                preview = "Your appointment has been confirmed for 15th November at 10:00 AM in the Cardiology Department...",
                timestamp = "2 hours ago",
                isRead = false,
                isArchived = false,
                isFlagged = false,
                hasAttachment = true
            ),
            Message(
                id = "2",
                sender = "Dr Sarah Johnson",
                subject = "Blood Test Results Available",
                preview = "Your recent blood test results are now available to view in your NHS account...",
                timestamp = "Yesterday",
                isRead = false,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            ),
            Message(
                id = "3",
                sender = "Oak Tree Surgery",
                subject = "Prescription Ready for Collection",
                preview = "Your repeat prescription is ready for collection at the pharmacy...",
                timestamp = "2 days ago",
                isRead = true,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            ),
            Message(
                id = "4",
                sender = "NHS App Team",
                subject = "New Feature: Video Consultations Now Available",
                preview = "We're excited to announce a new feature in the NHS App: Video Consultations!...",
                timestamp = "3 days ago",
                isRead = true,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            ),
            Message(
                id = "5",
                sender = "Manchester Royal Infirmary",
                subject = "Follow-up Appointment Required",
                preview = "Following your recent consultation with our specialist, we would like to schedule a follow-up...",
                timestamp = "1 week ago",
                isRead = true,
                isArchived = false,
                isFlagged = true,
                hasAttachment = false
            ),
            Message(
                id = "6",
                sender = "NHS Vaccination Centre",
                subject = "Winter Flu Vaccination Reminder",
                preview = "Winter is approaching, and it's time to protect yourself against flu...",
                timestamp = "1 week ago",
                isRead = true,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            ),
            Message(
                id = "7",
                sender = "Dr Michael Chen",
                subject = "Annual Medication Review Due",
                preview = "Your annual medication review is now due. This is an important check-up...",
                timestamp = "2 weeks ago",
                isRead = true,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            ),
            Message(
                id = "8",
                sender = "NHS 111",
                subject = "Your NHS 111 Online Consultation Summary",
                preview = "Thank you for using NHS 111 online for your recent health query...",
                timestamp = "3 weeks ago",
                isRead = true,
                isArchived = false,
                isFlagged = false,
                hasAttachment = false
            )
        )
    }
}