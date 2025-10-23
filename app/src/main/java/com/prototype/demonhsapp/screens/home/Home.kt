package com.prototype.demonhsapp.screens.home

import android.view.SoundEffectConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.prototype.demonhsapp.R
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.CampaignCard
import com.prototype.demonhsapp.components.ChromeCustomTab
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.components.WebViewButton
import com.prototype.demonhsapp.navigation.Routes
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4
import com.prototype.demonhsapp.ui.theme.nhsGrey5
import com.prototype.demonhsapp.ui.theme.nhsPurple
import com.prototype.demonhsapp.ui.theme.nhsYellow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
//                    val collapsedFraction = scrollBehavior.state.collapsedFraction
//                    val titleAlpha by animateFloatAsState(if (collapsedFraction > 0.2f) 1f else 0f)
//                    Text("Home", modifier = Modifier.alpha(titleAlpha), maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = (24 + (32 - 24)*(1-scrollBehavior.state.collapsedFraction)).sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = { },
                actions = {
                    HelpButton()
                    AccountButton()
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { },
        content = { values ->
            Box(modifier = Modifier.fillMaxSize().background(nhsBlue)) {
                val collapseFraction = scrollBehavior.state.collapsedFraction

                val headerAlpha by animateFloatAsState(1f - collapseFraction * 1.2f)
                val headerTranslationY = -collapseFraction * 150f
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .graphicsLayer {
                            translationY = headerTranslationY
                            alpha = headerAlpha
                        }
                        .padding(horizontal = 16.dp).padding(top = 128.dp)
                        .zIndex(-1f)
                ){
                    Header()
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(values)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 96.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Text("Services", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.prescriptions)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Request medicines", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ChromeCustomTab()
                            }
                        }
                    }

                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Your health", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("GP health record", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.prescriptions2)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Prescriptions", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.upcomingAndAastAppointments)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Upcoming and past appointments", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2)},
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Messages", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                        navController.navigate(Routes.yourMessages)
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("View your messages", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { Icon(Icons.Outlined.Email, contentDescription = null) },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2) },
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Section title
                    item() {
                        Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            Text("Account", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = nhsGrey)
                        }
                    }
                    // List
                    item() {
                        Card(Modifier.padding(bottom = 16.dp), colors = CardDefaults.cardColors(Color.White)) {
                            Column () {
                                ListItem(
                                    modifier = Modifier.clickable(onClick = {
                                    /*TODO*/
                                        view.playSoundEffect(SoundEffectConstants.CLICK)
                                    }),
                                    colors = ListItemDefaults.colors(Color.White) ,
                                    headlineContent = { Text("Manage services for another person", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
                                    leadingContent = { BadgedBox( badge = {Badge(content = { Text("2") })} ){ Icon(Icons.Outlined.SupervisedUserCircle, contentDescription = null)  } },
                                    overlineContent = { },
                                    trailingContent = { Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = nhsGrey2) },
                                    supportingContent = { }

                                )
                                HorizontalDivider(color = nhsGrey4)
                            }
                        }
                    }
                    //Start of section [Campaign card]
                    item() {

                        Column(modifier = Modifier.padding(bottom = 16.dp)) { CampaignCard() }
                    }
                    item() {

                        TextButton(onClick = {
                        /*TODO*/
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        }, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                            Row (verticalAlignment = Alignment.CenterVertically) { Icon(imageVector = Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = null)
                                Text("Get help using the NHS App", modifier = Modifier.padding(start = 8.dp), fontSize = 18.sp,)
                            }
                        }
                    }



                }
            }
        }
    )
}

@Composable
private fun Header(modifier: Modifier = Modifier){
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(nhsBlue)
            .padding(bottom = 16.dp)
    ) {
        // NHS logo and welcome message
        Image(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .height(22.dp)
                .width(54.dp),
            painter = painterResource(R.drawable.nhs_logo_2),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text("Good evening,", fontSize = 18.sp, color = Color.White)
        Text(
            "Mary Swanson",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.White
        )
        Row {
            Text("NHS number: ", fontSize = 18.sp, color = Color.White.copy(alpha = 0.7f))
            Text("123 456 7890", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}
//private fun Header(){
//    Column (modifier = Modifier.padding(bottom = 16.dp)) {
//        // NHS logo and welcome message
//        Image(modifier = Modifier
//            .padding(bottom = 24.dp)
//            .height(22.dp)
//            .width(54.dp),painter = painterResource(R.drawable.nhs_logo), contentDescription = null, contentScale = ContentScale.FillWidth)
//        Text("Good evening,", fontSize = 18.sp)
//        Text("Mary Swanson", fontSize = 32.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
//        Row {
//            Text("NHS number: ", fontSize = 18.sp, color = nhsGrey)
//            Text("123 456 7890", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = nhsBlue)
//        }
//    }
//}
@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun HomePreview() {
    Home(rememberNavController(), modifier = Modifier)
}