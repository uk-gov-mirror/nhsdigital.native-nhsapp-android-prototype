package com.prototype.nhsappdv1.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.SoundEffectConstants
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsGrey2
import com.prototype.nhsappdv1.ui.theme.nhsGrey4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChromeCustomTab(
    text: String,
    url: String,
    showDivider: Boolean = true,
    modifier: Modifier = Modifier
) {
    // Sound effects and haptics
    val view = LocalView.current
    val haptics = LocalHapticFeedback.current
    val ctx = LocalContext.current

    Column {
        // Button which triggers the remember state variable
        ListItem(
            modifier = modifier
                .clickable(onClick = {
                    openTab(ctx, url)
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                })
                .padding(vertical = 8.dp),
            colors = ListItemDefaults.colors(Color.Transparent),
            leadingContent = {
                Icon(
                    Icons.AutoMirrored.Outlined.OpenInNew,
                    contentDescription = null,
                    tint = nhsGrey2,
                    modifier = Modifier.size(24.dp)
                )
            },
            headlineContent = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    color = nhsBlack
                )
            },
            supportingContent = {
                Text(
                    text = Uri.parse(url).host ?: url,
                    style = MaterialTheme.typography.bodyMedium,
                    color = nhsGrey2
                )
            },
            tonalElevation = 0.dp
        )

        if (showDivider) {
            HorizontalDivider(
                color = nhsGrey4.copy(alpha = 0.3f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// on below line we are creating a function to open custom chrome tabs.
fun openTab(context: Context, url: String) {
    // on below line we are creating a variable for
    // package name and specifying package name as
    // package of chrome application.
    val package_name = "com.android.chrome"

    // on below line we are creating a variable
    // for the activity and initializing it.
    val activity = (context as? Activity)

    // on below line we are creating a variable for
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

    // on below line we are creating a
    // variable to build our builder.
    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    if (package_name != null) {
        // on below line if package name is not null
        // we are setting package name for our intent.
        customBuilder.intent.setPackage(package_name)

        // on below line we are calling launch url method
        // and passing url to it on below line.
        customBuilder.launchUrl(context, Uri.parse(url))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        // on below line we are calling start
        // activity to start the activity.
        activity?.startActivity(i)
    }
}

// Preview with single item
@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ChromeCustomTabPreview() {
    ChromeCustomTab(
        text = "Check if you need urgent medical help using 111 online",
        url = "https://111.nhs.uk"
    )
}

// Preview with multiple items showing all three NHS links
@Preview(showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun MultipleCustomTabsPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ChromeCustomTab(
            text = "Check your symptoms using 111 online",
            url = "https://111.nhs.uk"
        )
        ChromeCustomTab(
            text = "Health A to Z",
            url = "https://www.nhs.uk/health-a-to-z/"
        )
        ChromeCustomTab(
            text = "Find services near you",
            url = "https://www.nhs.uk/nhs-services/",
            showDivider = false // No divider on last item
        )
    }
}