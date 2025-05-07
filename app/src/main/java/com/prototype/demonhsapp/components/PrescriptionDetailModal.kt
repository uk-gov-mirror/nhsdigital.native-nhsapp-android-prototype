package com.prototype.demonhsapp.components


import android.view.SoundEffectConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.prototype.demonhsapp.d.R
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGreenTint
import com.prototype.demonhsapp.ui.theme.nhsGreenTone
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import com.prototype.demonhsapp.ui.theme.nhsRedTint
import com.prototype.demonhsapp.ui.theme.nhsRedTone
import kotlinx.coroutines.launch


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailModal(){
    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    // Remember modal state and bottom sheet state
    var showDetail by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Button that triggers the modal to show details
    Column () {
        ListItem(
            modifier = Modifier.clickable(onClick = {
                showDetail = !showDetail
                view.playSoundEffect(SoundEffectConstants.CLICK)
            }),
            colors = ListItemDefaults.colors(Color.White) ,
            headlineContent = { Text("One off prescription", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
            leadingContent = { },
            overlineContent = {
                Card(colors = CardDefaults.cardColors(
                    nhsGreenTint
                )) { Text("Ready to collect", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsGreenTone) }
            },
            trailingContent = { },
            supportingContent = {
                Column {
                    Text("Prescribed on 20 Jul 2024 by Dr. Smith", modifier = Modifier.padding(bottom = 8.dp), color = nhsGrey)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Badge()
                        Text("1 item cancelled", modifier = Modifier.padding(start = 8.dp), fontSize = 14.sp)
                    }

                }
            }

        )
        HorizontalDivider(color = nhsGrey4)
    }

    // Animation which is triggered to show detail screen
    AnimatedVisibility(
        showDetail,
        enter = scaleIn(),
        exit = scaleOut()
    ){
        Dialog(
            onDismissRequest = { showDetail = false},
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            //Dialog content here
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    MediumTopAppBar(
                        title = {
                            Text("One off prescription", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                showDetail = false
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close details"
                                )
                            }
                        },
                        actions = { },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
                        scrollBehavior = scrollBehavior
                    )
                },
                bottomBar = {
                    BottomAppBar(modifier = Modifier.height(150.dp).padding(horizontal = 16.dp), containerColor = nhsGrey5, content = { Column {
                        TextButton(onClick = {
                            showBottomSheet = !showBottomSheet
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                             },
                            colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)
                        ) {
                            Row (verticalAlignment = Alignment.CenterVertically) { Icon(imageVector = Icons.Outlined.QrCode, contentDescription = null)
                                Text("Show barcode", modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp,)
                            }
                        }
                    } })
                },
                content = { values ->
                    Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(values)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            item() {

                                Column (modifier = Modifier.padding(bottom = 24.dp)) {
                                    Text("Prescribed on 20 July 2024 by Dr. Smith", fontSize = 20.sp, color = nhsGrey)
                                }
                            }
                            //Section title
                            item() {
                                Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                                    Text("Details", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                                }
                            }
                            // List
                            item() {
                                Column (modifier = Modifier.padding(bottom = 24.dp)) {
                                    Text("To be collect at Boots Pharmacy, Balham, London, SW12 73G.\r\n Phone number 080867766155.", fontSize = 16.sp)
                                }
                                Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.Transparent)) {
                                    Column () {
                                        ListItem(
                                            modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                            colors = ListItemDefaults.colors(Color.Transparent) ,
                                            headlineContent = { Text("Cephalexin 500mg capsules", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                            overlineContent = {
                                                Card(
                                                    colors = CardDefaults.cardColors(nhsGreenTint)
                                                ) { Text("Ready to collect", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsGreenTone) }
                                            },
                                            trailingContent = { },
                                            supportingContent = {
                                                Text("Quantity: 28 capsules", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                            }

                                        )
                                        HorizontalDivider(color = nhsGrey4)
                                    }
                                    Column () {
                                        ListItem(
                                            modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = { /*TODO*/}),
                                            colors = ListItemDefaults.colors(Color.Transparent) ,
                                            headlineContent = { Text("Codeine phosphate 30mg tablets", modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp)) },
                                            overlineContent = {
                                                Card(
                                                    colors = CardDefaults.cardColors(nhsRedTint)
                                                ) { Text("Cancelled", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = nhsRedTone) }
                                            },
                                            trailingContent = { },
                                            supportingContent = {
                                                Text("Quantity: 10 capsules", modifier = Modifier.padding(bottom = 8.dp, start = 0.dp))
                                            }

                                        )
                                        HorizontalDivider(color = nhsGrey4)
                                    }
                                    Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                                        Text("For instructions on how to take your medicine, read the label on the packet or container.")
                                    }
                                }
                            }




                        }

                        //Barcode bottom sheet
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
                                            Text("Barcode", style = MaterialTheme.typography.titleLarge)
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        HorizontalDivider()
                                    }
                                }
                            ) {
                                // Content inside the sheet goes here
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 24.dp)) {
                                    Image(painterResource(R.drawable.barcode), contentDescription = null, contentScale = ContentScale.Fit)
                                    Text("ID: 1234 5678 9101", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                                    Text("Only show this barcode if your pharmacist asks.", modifier = Modifier.padding(bottom = 8.dp))
                                    Image(painterResource(R.drawable.save_to_google_pay___english__dark_), contentDescription = null, contentScale = ContentScale.Fit)
                                }

                            }
                        }
                    }
                }
            )

            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionDetailContent(onClick: () -> Unit){
//    Surface {
//        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp)) {
//            Text("One of prescription")
//            Text("Prescribed on 20 July 2024 by Dr. Smith")
//            Text("Details")
//            Text("To be collected at Boots Pharmacy, Balham, London, SW12 73G. Phone number: 080867766155")
//        }
//    }



}

@Preview
@Composable
fun DetailPreview(){
    PrescriptionDetailContent {  }
}
