package com.prototype.demonhsapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.prototype.demonhsapp.screens.yourhealth.prescriptions.PastPrescriptions2
import com.prototype.demonhsapp.screens.yourhealth.prescriptions.RecentPrescriptions
import com.prototype.demonhsapp.ui.theme.nhsBlack
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey5


@Preview
@Composable
fun TabScreen() {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Recent", "Past")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = nhsGrey5,
            indicator = { tabPositions ->
                if (tabIndex < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = nhsBlue
                    )
                }
            },
            contentColor = nhsBlue
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title, fontWeight = if (tabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    color = if (tabIndex == index) nhsBlue else nhsBlack
                ) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> RecentPrescriptions(rememberNavController(), modifier = Modifier)
            1 -> PastPrescriptions2(rememberNavController(), modifier = Modifier)
        }
    }
}