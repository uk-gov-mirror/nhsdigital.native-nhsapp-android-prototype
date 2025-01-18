package com.prototype.demonhsapp.components

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import kotlinx.coroutines.launch

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewButton() {

    // Remember variable which shows or hides the sheet
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Button which triggers the remember state variable
    ListItem(
        modifier = Modifier.clickable(onClick = {showBottomSheet = true}),
        colors = ListItemDefaults.colors(Color.White) ,
        headlineContent = { Text("Check if you need urgent medical help using 111 online", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
        leadingContent = { },
        overlineContent = { },
        trailingContent = { Icon(Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null, tint = nhsGrey2)},
        supportingContent = { }

    )
    HorizontalDivider(color = nhsGrey4)

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
                        Text("www.111.nhs.uk", style = MaterialTheme.typography.titleLarge)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                }
            }
        ) {
            // Content inside the sheet goes here
            LazyColumn(contentPadding = PaddingValues(0.dp)) {
                item() {
                    WebView()
                }

            }

        }
    }
}

