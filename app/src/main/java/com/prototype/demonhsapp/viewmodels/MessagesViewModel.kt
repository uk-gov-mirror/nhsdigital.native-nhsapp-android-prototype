package com.prototype.demonhsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.prototype.demonhsapp.screens.messages.Message
import com.prototype.demonhsapp.screens.messages.getSampleMessages

class MessagesViewModel : ViewModel() {

    private val _messages = mutableStateOf(getSampleMessages())
    val messages: State<List<Message>> = _messages

    val unreadCount: Int
        get() = _messages.value.count { !it.isRead }

    fun markAsRead(messageId: String) {
        _messages.value = _messages.value.map {
            if (it.id == messageId) it.copy(isRead = true) else it
        }
    }

    fun markAsUnread(messageId: String) {
        _messages.value = _messages.value.map {
            if (it.id == messageId) it.copy(isRead = false) else it
        }
    }

    fun deleteMessage(messageId: String) {
        _messages.value = _messages.value.filter { it.id != messageId }
    }
}