package com.prototype.demonhsapp.components

import android.view.SoundEffectConstants
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsPaleYellow
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTooltipSample() {
    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = { Text("Request new prescriptions", fontSize = 20.sp) },
                colors = TooltipDefaults.richTooltipColors(nhsPaleYellow),
                action = {
                    TextButton(
                        onClick = {
                            scope.launch { tooltipState.dismiss() }
                            view.playSoundEffect(SoundEffectConstants.CLICK)
//                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                  },
                        colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)
                    ) {
                        Text("Learn more", fontSize = 16.sp)
                    }
                },
                caretSize = TooltipDefaults.caretSize
            ) {
                //Body copy within tips
                Text("Make your prescription orders from here", fontSize = 16.sp)
            }
        },
        state = tooltipState
    ) {
        //Content here
        FloatingActionButton(
            onClick = {
                scope.launch { tooltipState.show() }
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                      },
            containerColor = Color.White
        ) {
            Icon(imageVector = Icons.Outlined.Medication, contentDescription = "Request new prescription", tint = nhsBlue)}
    }
}