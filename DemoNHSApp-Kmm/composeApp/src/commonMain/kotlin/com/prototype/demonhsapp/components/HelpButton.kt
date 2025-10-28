package com.prototype.demonhsapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.screens.help.HelpSupport
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpButton() {

    // Sound effects and haptics
    val haptics = LocalHapticFeedback.current

    // Remember variable which shows or hides the sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Button which triggers the remember state variable
    IconButton(onClick = {
        showBottomSheet = true
//        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
            contentDescription = "Help and support",
            tint = nhsGrey
        )
    }

    // Condition which shows the sheet if the remember state is changed
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {showBottomSheet = false},
            containerColor = nhsGrey5,
            dragHandle = {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                BottomSheetDefaults.DragHandle()
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    IconButton( onClick = {
                        scope.launch { sheetState.hide()}.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }) { Icon(imageVector = Icons.Default.Close, contentDescription = "Close") }
                    Text("Help and support", fontSize = 24.sp, fontWeight = FontWeight.Normal)
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
            }
            }
        ) {
            // Content inside the sheet goes here
            HelpSupport(rememberNavController())

        }
    }
}

