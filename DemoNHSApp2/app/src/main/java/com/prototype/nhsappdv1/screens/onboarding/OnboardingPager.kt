package com.prototype.nhsappdv1.screens.onboarding

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappdv1.R
import com.prototype.nhsappdv1.components.LottieAnimation
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsBlue
import com.prototype.nhsappdv1.ui.theme.nhsGrey3
import com.prototype.nhsappdv1.ui.theme.nhsGrey5
import kotlinx.coroutines.launch


@Composable
fun OnboardingPager(onFinished: () -> Unit){

    // Sound and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    // Remember page count
    val pagerState = rememberPagerState(pageCount = {3})
    val coroutineScope = rememberCoroutineScope()

    // Track current page for accessibility reset
    val previousPage = remember { mutableStateOf(0) }

    // Reset accessibility traversal when page changes
    LaunchedEffect(pagerState.currentPage) {
        if (previousPage.value != pagerState.currentPage) {
            // Clear any existing focus
            view.clearFocus()
            // Small delay to ensure page transition is complete
            kotlinx.coroutines.delay(100)
            previousPage.value = pagerState.currentPage
        }
    }

    Scaffold(
        topBar = { },
        bottomBar = {
            // Add bottom app bar controls here
            BottomAppBar( containerColor = nhsGrey5, content = {

                Row( modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    if (pagerState.currentPage > 0) { TextButton(onClick = {
                        coroutineScope.launch() { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                        Row (verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                            Text("Previous", modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp,)
                        }
                    } }

                    Spacer(modifier = Modifier.padding(horizontal = 24.dp))

                    if (pagerState.currentPage == 2) { TextButton(
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
            //Background
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize().padding(values)) {
                // Pager function
                HorizontalPager(state = pagerState) { page ->
                    // Our page content
                    if (page == 0){ OnboardingScreen1() }
                    else if(page == 1) { OnboardingScreen2() }
                    else if(page == 2) { OnboardingScreen3(isCurrentPage = pagerState.currentPage == 2) }
//                    else if (page == 3) { OnboardingScreen4() }
//                    else if (page == 4) { OnboardingScreen5() }

                }
                // Indicator
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


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen1(){
    val configuration = LocalConfiguration.current.orientation

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp).clearAndSetSemantics { contentDescription = "Welcome to the NHS App. Before you start, here are some tips about using the app. Page 1 of 3. Swipe right or left with two fingers to go forward or back" }, verticalArrangement = Arrangement.Bottom) {

        if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
            Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 48.dp)) {
                Column (modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(24.dp)
                    .width(54.dp)) {
                    Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
                }
                Column {
                    Text("Welcome to the NHS App", modifier = Modifier.padding(bottom = 8.dp), fontSize = 48.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    Text("Before you start, here are some tips about using the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 112.dp))
        } else {
            Column() {
                Column (modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(24.dp)
                    .width(54.dp)) {
                    Image(painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
                }
                Column {
                    Text("Welcome to the NHS App", modifier = Modifier.padding(bottom = 8.dp), fontSize = 48.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    Text("Before you start, here are some tips about using the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 112.dp))
        }

    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen2(){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current.orientation

    if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 48.dp).clearAndSetSemantics { contentDescription = "When you start using the app, you can use the menu at the bottom of the screen to move between the main areas. Page 2 of 3. Swipe right or left with two fingers to go forward or back" }) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
//                Column(modifier = Modifier.width(256.dp)) { Image(painterResource(R.drawable.app_menu_image), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth()) }
                Column(modifier = Modifier.width(256.dp)) { LottieAnimation() }

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("Moving around", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    Text("Use the menu at the bottom of the screen to move between the main areas of the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                }
            }

        }
    } else {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 16.dp).clearAndSetSemantics { contentDescription = "When you start using the app, you can use the menu at the bottom of the screen to move between the main areas. Page 2 of 3. Swipe right or left with two fingers to go forward or back" }) {

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
//            Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_menu_image), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth()) }
            Column(modifier = Modifier.fillMaxSize().height(524.dp).padding(bottom = 16.dp)) { LottieAnimation() }

            Column() {
                Text("Moving around", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                Text("Use the menu at the bottom of the screen to move between the main areas of the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            }

        }
    }

}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen3(isCurrentPage: Boolean = true){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current.orientation
    val focusRequester = remember { FocusRequester() }
    val hasFocused = remember { mutableStateOf(false) }

    LaunchedEffect(isCurrentPage) {
        if (isCurrentPage && !hasFocused.value) {
            // Add a longer delay to ensure TalkBack is ready
            kotlinx.coroutines.delay(300)
            try {
                focusRequester.requestFocus()
                hasFocused.value = true
            } catch (e: Exception) {
                // Handle any focus request failures gracefully
            }
        }
        // Reset the flag and clear focus when leaving the page
        if (!isCurrentPage) {
            hasFocused.value = false
            focusRequester.freeFocus()
        }
    }

    if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 48.dp).semantics { isTraversalGroup = true }) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
//                Column(modifier = Modifier.width(256.dp)) { LottieAnimation() }

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("What you can do in the app", modifier = Modifier.padding(bottom = 24.dp).focusRequester(focusRequester).focusable().semantics {
                        traversalIndex = 0f
                        contentDescription = "Here's what you can do in the app"
                        heading()
                    }, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 1f
                        contentDescription = "The first area of the app is called 'services'. Here you will find options to request repeat prescriptions, book GP appointments and get other NHS support." }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.LocalHospital,
                                contentDescription = null,
                                tint = nhsBlue,
                                modifier = Modifier.size(34.dp)
                            )
                            Column (modifier = Modifier.padding(start = 8.dp)) {
                                Text("Home", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                                Text("Request repeat prescriptions, GP appointments and other NHS support.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))

                            }
                        }
                    }
                    Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 2f
                        contentDescription = "The second area of the app is called 'your health'. Here you can check your booked appointments, prescription requests and health records." }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = nhsBlue,
                                modifier = Modifier.size(34.dp)
                            )
                            Column (modifier = Modifier.padding(start = 8.dp)) {
                                Text("Your health", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                                Text("Check your booked appointments, prescription requests and health records.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 3f
                        contentDescription = "The third area of the app is your messages. Here you can read messages from your GP surgery and other healthcare services. Now, you can get started. Select the 'done' button to start using the app." }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Mail,
                                contentDescription = null,
                                tint = nhsBlue,
                                modifier = Modifier.size(34.dp)
                            )
                            Column (modifier = Modifier.padding(start = 8.dp)) {
                                Text("Messages", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                                Text("Read messages from your healthcare services.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                            }
                        }
                    }
                }
            }

        }
    } else {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 16.dp).semantics { isTraversalGroup = true }) {

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

//             Column(modifier = Modifier.fillMaxSize().height(524.dp)) { LottieAnimation() }

            Text("What you can do in the app", modifier = Modifier.padding(bottom = 24.dp).focusRequester(focusRequester).focusable().semantics {
                traversalIndex = 0f
                contentDescription = "Here's what you can do in the app"
                heading()
            }, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
            Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 1f
                contentDescription = "The first area of the app is called 'services'. Here you will find options to request repeat prescriptions, book GP appointments and get other NHS support." }) {
                Row {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = nhsBlue,
                        modifier = Modifier.size(34.dp)
                    )
                    Column (modifier = Modifier.padding(start = 8.dp)) {
                      Text("Home", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                        Text("Request repeat prescriptions, GP appointments and other NHS support.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))

                    }
                }
            }
            Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 2f
                contentDescription = "The second area of the app is called 'your health'. Here you can check your booked appointments, prescription requests and health records." }) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = nhsBlue,
                        modifier = Modifier.size(34.dp)
                    )
                    Column (modifier = Modifier.padding(start = 8.dp)) {
                        Text("Your health", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                        Text("Check your booked appointments, prescription requests and health records.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            }
            Column(modifier = Modifier.padding(bottom = 24.dp).clearAndSetSemantics { traversalIndex = 3f
                contentDescription = "The third area of the app is your messages. Here you can read messages from your GP surgery and other healthcare services. Now, you can get started. Select the 'done' button to start using the app." }) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = null,
                        tint = nhsBlue,
                        modifier = Modifier.size(34.dp)
                    )
                    Column (modifier = Modifier.padding(start = 8.dp)) {
                        Text("Messages", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue, modifier = Modifier.padding(bottom = 8.dp))
                        Text("Read messages from your healthcare services.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            }

        }
    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen4(){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current.orientation

    if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 48.dp).clearAndSetSemantics { contentDescription = "Use the 'your health' area in the bottom menu to view records and manage your healthcare. In that area, you can access your GP health record, manage GP and hospital appointments and check on your prescription requests. Page 4 of 5. Swipe right or left with two fingers to go forward or back" }) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.width(256.dp)) { Image(painterResource(R.drawable.app_your_health_image), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth()) }

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("Your Health", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    val multipleLineText = buildAnnotatedString {
                        append("Select ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) { append("Your Health")}
                        append(" to view records and manage your healthcare.")
                    }
                    Text(multipleLineText, fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                    Text("You can: \n• access your GP health record \n• manage GP and hospital appointments \n• check on your prescription requests ", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                }
            }

        }
    } else {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 16.dp).clearAndSetSemantics { contentDescription = "Use the 'your health' area in the bottom menu to view records and manage your healthcare. In that area, you can access your GP health record, manage GP and hospital appointments and check on your prescription requests. Page 4 of 5. Swipe right or left with two fingers to go forward or back" }) {

            Spacer(modifier = Modifier.padding(vertical = 64.dp))
            Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_your_health_image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth().height(300.dp)) }

            Column() {
                Text("Your Health", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                val multipleLineText = buildAnnotatedString {
                    append("Select ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) { append("Your Health")}
                    append(" to view records and manage your healthcare.")
                }
                Text(multipleLineText, fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                Text("You can: \n• access your GP health record \n• manage GP and hospital appointments \n• check on your prescription requests ", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            }

        }
    }
}

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingScreen5(){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current.orientation

    if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 48.dp).clearAndSetSemantics { contentDescription = "Use the messages area in the bottom menu to read messages from your healthcare services. You can turn on notifications for these in the account and settings menu area of the app. Page 5 of 5. Now, you can get started. Select the 'done' button below to start using the app." }) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.width(256.dp)) { Image(painterResource(R.drawable.app_messages_image), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth()) }

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("Messages", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    val multipleLineText = buildAnnotatedString {
                        append("Select ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) { append("Messages")}
                        append(" to read messages from your healthcare services.")
                    }
                    Text(multipleLineText, fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                    Text("You can turn on notifications for these in the account and settings area of the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                }
            }

        }
    } else {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(vertical = 24.dp, horizontal = 16.dp).clearAndSetSemantics { contentDescription = "Use the messages area in the bottom menu to read messages from your healthcare services. You can turn on notifications for these in the account and settings menu area of the app. Page 5 of 5. Now, you can get started. Select the 'done' button below to start using the app." }) {

            Spacer(modifier = Modifier.padding(vertical = 64.dp))
            Column(modifier = Modifier.fillMaxWidth()) { Image(painterResource(R.drawable.app_messages_image), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth().height(300.dp)) }

            Column() {
                Text("Messages", modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                val multipleLineText = buildAnnotatedString {
                    append("Select ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) { append("Messages")}
                    append(" to read messages from your healthcare services.")
                }
                Text(multipleLineText, fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
                Text("You can turn on notifications for these in the account and settings area of the app.", fontSize = 16.sp, color = nhsBlack, modifier = Modifier.padding(bottom = 8.dp))
            }

        }
    }
}


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun OnboardingPagerPreview(){
    val context = LocalContext.current
    // Show toast message on click of done button
    OnboardingPager { Toast.makeText(context, "Onboarding complete", Toast.LENGTH_SHORT).show() }
}

