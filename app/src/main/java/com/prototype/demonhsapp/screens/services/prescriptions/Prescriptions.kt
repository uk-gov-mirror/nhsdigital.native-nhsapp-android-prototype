package com.prototype.demonhsapp.screens.services.prescriptions



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.components.AccountButton
import com.prototype.demonhsapp.components.ButtonPrimary
import com.prototype.demonhsapp.components.HelpButton
import com.prototype.demonhsapp.ui.theme.nhsBlue
import com.prototype.demonhsapp.ui.theme.nhsGrey5


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Prescriptions(navController: NavController, modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Request medicines", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 32.sp, fontWeight = FontWeight.Normal)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back to previous screen"
                        )
                    }
                },
                actions = {
                    HelpButton()
                    AccountButton()
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = nhsGrey5, scrolledContainerColor = nhsGrey5),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(modifier.height(150.dp).padding(horizontal = 16.dp), containerColor = nhsGrey5, content = { Column {
                TextButton(onClick = { /*TODO*/}, colors = ButtonDefaults.textButtonColors(contentColor = nhsBlue)) {
                    Row (verticalAlignment = Alignment.CenterVertically) { Icon(imageVector = Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = null)
                        Text("Help with medical abbreviations", modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp,)
                    }
                }
                ButtonPrimary("Continue") { }
            } })
        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(values).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item() {

                        Column (modifier = Modifier.padding(bottom = 24.dp)) {
                            Text("Content for requesting medicines here", fontSize = 16.sp)
                        }
                    }
                    items(50) {
                        ListItem(
                            headlineContent = { Text("Item $it")},
                            leadingContent = { Icon(imageVector = Icons.Default.CropSquare, contentDescription = null) },
                            colors = ListItemDefaults.colors(containerColor = nhsGrey5)
                        )
                    }


                }
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun PrescriptionsPreview(){
    Prescriptions(rememberNavController(), modifier = Modifier)
}