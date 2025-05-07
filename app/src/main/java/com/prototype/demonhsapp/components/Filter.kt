package com.prototype.demonhsapp.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import kotlinx.coroutines.launch

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filter() {

    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    // Remember variable which shows or hides the sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Button which triggers the remember state variable
    TextButton(onClick = {
        showBottomSheet = true
        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                         },
        modifier = Modifier.padding(bottom = 8.dp)) {
        Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = nhsBlue, modifier = Modifier.padding(end = 8.dp))
        Text("Filter", color = nhsBlue, fontSize = 16.sp)
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
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                    }) { Icon(imageVector = Icons.Default.Close, contentDescription = "Close") }
                    Text("Filter by", fontSize = 24.sp, fontWeight = FontWeight.Normal)
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
            }
            }
        ) {
            // Content inside the sheet goes here
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(50) {
                    ListItem(
                        headlineContent = { Text("Item $it")},
                        leadingContent = { Icon(imageVector = Icons.Default.CropSquare, contentDescription = null) },
                        colors = ListItemDefaults.colors(containerColor = nhsGrey5)
                    )
                }

                // Dismiss button within the content body
                item {
                    ButtonSecondary("Cancel", onClick = {
                        scope.launch { sheetState.hide()}.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    })
                }
            }

        }
    }
}

