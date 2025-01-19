package com.prototype.demonhsapp.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.R
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey3
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import kotlinx.coroutines.launch



@Composable
fun OnboardingCarousel(onFinished: () -> Unit){

    // Sound and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    // Remember page count
    val pagerState = rememberPagerState(pageCount = {4})
    val coroutineScope = rememberCoroutineScope()

    // Pager function
    HorizontalPager(state = pagerState) { page ->
        // Our page content
        Scaffold(
            topBar = { },
            bottomBar = {
                // Add bottom app bar controls here
                BottomAppBar( containerColor = nhsGrey5, content = {

                    Row( modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                        if (page > 0) { TextButton(onClick = {
                            coroutineScope.launch() { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                             }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                                Text("Previous", modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp,)
                            }
                        } }

                        Spacer(modifier = Modifier.padding(horizontal = 24.dp))

                        if (page == 3) { TextButton(
                            onClick = onFinished,
                            colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                            Row (verticalAlignment = Alignment.CenterVertically) {

                                Text("Done", modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp,)
                            }
                        } } else TextButton(onClick = {
                            coroutineScope.launch() { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                      }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text("Next", modifier = Modifier.padding(end = 8.dp), fontSize = 16.sp,)
                                Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null)
                            }
                        }
                    }
                }
                )
            },
            content = { values ->
                Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize().padding(values)) {
                    if (page == 0){ OnboardingScreen1() }
                    else if(page == 1) { OnboardingScreen2() }
                    else if(page == 2) { OnboardingScreen3() }
                    else if (page == 3) { OnboardingScreen4() }

                    Column(verticalArrangement = Arrangement.Bottom) {
                        Row(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color = if (pagerState.currentPage == iteration) nhsBlack else nhsGrey3
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(8.dp)
                                )
                            }
                        }
                    }

                }

            }
        )
    }
}


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen1(){
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp)) {
        Column (modifier = Modifier
            .padding(bottom = 24.dp)
            .height(22.dp)
            .width(54.dp)) {
            Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
        }
        Spacer(modifier = Modifier.padding(vertical = 64.dp))

        Column() {
            Text("Welcome to the NHS App", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
            Text("There's a new way to navigate the app. It's now easier to find services to manage your health.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("Let's get started", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
        }

    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen2(){
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp)) {

        Spacer(modifier = Modifier.padding(vertical = 64.dp))
        Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_services_image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth().height(300.dp)) }

        Column() {
            Text("Access services", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
            Text("In 'Services', you can take actions to look after your health.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("You can request repeat prescriptions, check for available GP appointments and more.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
        }

    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen3(){
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp)) {

        Spacer(modifier = Modifier.padding(vertical = 64.dp))
        Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_your_health_image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth().height(300.dp)) }

        Column() {
            Text("Manage your health", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
            Text("In 'Your health', you can view and manage your health information.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("You can check your booked appointments, request prescriptions and GP health record.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
        }

    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen4(){
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp)) {

        Spacer(modifier = Modifier.padding(vertical = 64.dp))
        Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_messages_image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth().height(300.dp)) }

        Column() {
            Text("View your messages", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
            Text("You may receive messages from your GP surgery and hospitals.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("You can turn on notifications in your account and settings.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            Text("Done", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
        }

    }
}


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun CarouselPreview(){
    val context = LocalContext.current
    // Show toast message on click of done button
    OnboardingCarousel { Toast.makeText(context, "Onboarding complete", Toast.LENGTH_SHORT).show() }
}

