package com.prototype.demonhsapp.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.SoundEffectConstants
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.demonhsapp.ui.theme.nhsGrey2
import com.prototype.demonhsapp.ui.theme.nhsGrey4

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChromeCustomTab() {

    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val ctx = LocalContext.current


    // Button which triggers the remember state variable
    ListItem(
        modifier = Modifier.clickable(onClick = {
            openTab(ctx)
            view.playSoundEffect(SoundEffectConstants.CLICK)
        }),
        colors = ListItemDefaults.colors(Color.White) ,
        headlineContent = { Text("Check if you need urgent medical help using 111 online", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) },
        leadingContent = { },
        overlineContent = { },
        trailingContent = { Icon(Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null, tint = nhsGrey2)},
        supportingContent = { }

    )
    HorizontalDivider(color = nhsGrey4)
}

// on below line we are creating demonhsapp function to open custom chrome tabs.
fun openTab(context: Context) {
    // on below line we are creating demonhsapp variable for
    // package name and specifying package name as
    // package of chrome application.
    val package_name = "com.android.chrome"

    // on below line we are creating demonhsapp variable for
    // our URL which we have to open in chrome tabs
    val URL = "https://111.nhs.uk"

    // on below line we are creating demonhsapp variable
    // for the activity and initializing it.
    val activity = (context as? Activity)

    // on below line we are creating demonhsapp variable for
    // our builder and initializing it with
    // custom tabs intent
    val builder = CustomTabsIntent.Builder()

    // on below line we are setting show title
    // to true to display the title for
    // our chrome tabs.
    builder.setShowTitle(true)

    // on below line we are enabling instant
    // app to open if it is available.
    builder.setInstantAppsEnabled(true)

    // on below line we are setting tool bar color for our custom chrome tabs.
//    builder.setToolbarColor(ContextCompat.getColor(context, R.color.purple_200))

    // on below line we are creating demonhsapp
    // variable to build our builder.
    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    if (package_name != null) {
        // on below line if package name is not null
        // we are setting package name for our intent.
        customBuilder.intent.setPackage(package_name)

        // on below line we are calling launch url method
        // and passing url to it on below line.
        customBuilder.launchUrl(context, Uri.parse(URL))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(URL))

        // on below line we are calling start
        // activity to start the activity.
        activity?.startActivity(i)
    }

}