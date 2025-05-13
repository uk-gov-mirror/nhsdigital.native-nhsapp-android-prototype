package com.prototype.demonhsapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.dotlottie.dlplayer.Mode


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LottieAnimation() {
    DotLottieAnimation(

        source = DotLottieSource.Asset("rnib_animation_testb.lottie"),
        autoplay = true,
        loop = true,
        modifier = Modifier.background(Color.Transparent).fillMaxSize()
    )
}



@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun LottieAnimationPreview(){
    Column {
        LottieAnimation()
    }
}