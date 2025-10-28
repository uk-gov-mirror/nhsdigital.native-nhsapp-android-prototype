package com.prototype.demonhsapp.navigation

import kotlinx.serialization.Serializable

object Routes {
    var screenA = "screen_A"
    var screenB = "screen_B"
    var bottomNav = "BottomNav"
    // Main hubs
    var home = "home"
    var services = "services"
    var yourHealth = "yourHealth"
    //Children screens
    var yourMessages = "yourMessages"
    var prescriptions = "prescriptions"
    var prescriptions2 = "prescriptions2"
    var viewManagePrescriptions = "viewManagePrescriptions"
    var checkPrescriptions = "checkPrescriptions"
    var pastPrescriptions = "pastPrescriptions"
    var prescriptionsDetail = "prescriptionsDetail"
    var upcomingAndAastAppointments = "upcomingAndPastAppointments"
    var referrals = "referrals"

    // Independent screens
    var accountsettings = "accountSettings"

    // ... your existing routes ...Messages

    const val messages = "messages"
    const val messageDetail = "message_detail/{messageId}"

    // Helper function to create route with message ID
    fun messageDetailRoute(messageId: String) = "message_detail/$messageId"
}

@Serializable
data class MessageDetailRoute(val messageId: String)