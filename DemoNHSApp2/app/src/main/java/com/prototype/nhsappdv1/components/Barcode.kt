package com.prototype.nhsappdv1.components

import android.view.SoundEffectConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappdv1.R
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey4


@Preview
@Composable
fun Barcode(){
    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    var showBarcode by remember { mutableStateOf(false) }

    Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column () {
            ListItem(
                modifier = Modifier.padding(horizontal = 0.dp).clickable(onClick = {
                    showBarcode = !showBarcode
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                }),
                colors = ListItemDefaults.colors(Color.White) ,
                headlineContent = { Text("Your prescription barcode", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, start = 0.dp), color = nhsBlue, fontWeight = FontWeight.SemiBold) },
                overlineContent = { },
                leadingContent = { if (showBarcode) { Icon(Icons.Outlined.RemoveCircleOutline, contentDescription = null, tint = nhsBlue, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))} else Icon(Icons.Outlined.AddCircleOutline, contentDescription = null, tint = nhsBlue, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) },
                trailingContent = { },
                supportingContent = {
                    AnimatedVisibility(showBarcode) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painterResource(R.drawable.barcode), contentDescription = null, contentScale = ContentScale.Fit)
                            Text("ID: 1234 5678 9101", fontWeight = FontWeight.SemiBold)
                            Text("Only show this barcode if your pharmacist asks.")
                            Image(painterResource(R.drawable.save_to_google_pay___english__dark_), contentDescription = null, contentScale = ContentScale.Fit)
                        }
                    }
                }

            )
            HorizontalDivider(color = nhsGrey4)
        }
    }
}