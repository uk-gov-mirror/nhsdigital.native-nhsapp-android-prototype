package com.nhs.online.nhsonline.proto.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nhs.online.nhsonline.design.components.NHSCard
import com.nhs.online.nhsonline.design.components.NHSCardUI
import com.nhs.online.nhsonline.design.theme.NHSAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(topBar = {
        TopAppBar(title = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = "Help")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Help")
                }
            })
    }) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(contentPadding).padding(horizontal = 16.dp)) {
            NHSCard(NHSCardUI("Mary Swanson", "123 456 7890", "Good evening"))
            Text("Home")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    NHSAppTheme {
        HomeScreen()
    }
}