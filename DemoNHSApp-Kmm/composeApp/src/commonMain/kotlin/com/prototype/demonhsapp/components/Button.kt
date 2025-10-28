package com.prototype.demonhsapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ButtonPrimary(text:String, onClick: () -> Unit) {
    // Sound effects and haptics
    val haptics = LocalHapticFeedback.current

   Button(
        onClick = {
            onClick()
//            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                  },
        modifier = Modifier
            .padding(bottom = 24.dp)
//            .shadow(elevation = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        enabled = true,
        colors = ButtonDefaults.buttonColors(containerColor = nhsGreen),
//        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Composable
fun ButtonSecondary (text:String, onClick: () -> Unit) {
    // Sound effects and haptics
    val haptics = LocalHapticFeedback.current

    OutlinedButton (
        onClick = {
            onClick()
            //            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                  },
        modifier = Modifier
            .padding(bottom = 24.dp)
//            .shadow(elevation = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        enabled = true,
        border = BorderStroke(2.dp, nhsBlue),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = nhsBlue),
//        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Composable
fun ButtonTetiary (text:String, onClick: () -> Unit) {
    // Sound effects and haptics
    val haptics = LocalHapticFeedback.current

    TextButton (
        onClick = {
            onClick()
            //            view.playSoundEffect(SoundEffectConstants.CLICK)
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                  },
        modifier = Modifier
            .padding(bottom = 24.dp)
//            .shadow(elevation = 16.dp)
            .fillMaxWidth(),
        enabled = true,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = nhsBlue),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview (){
    ButtonPrimary("Get new prescription", onClick = {/*TODO*/})
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview2 (){
    ButtonSecondary("Get new prescription", onClick = {/*TODO*/})
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview3 (){
    ButtonTetiary("Get new prescription", onClick = {/*TODO*/})
}