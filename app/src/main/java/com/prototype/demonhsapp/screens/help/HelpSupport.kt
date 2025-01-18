package com.prototype.demonhsapp.screens.help


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.prototype.demonhsapp.ui.theme.nhsGrey5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupport(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { },
        bottomBar = {

        },
        content = { values ->
            Surface(color = nhsGrey5, modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.padding(values).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {

                    //Message item
                    item() {
                        Column {
                            Text("This is the screen where we provide contextual help, FAQ's and support contact etc", fontSize = 16.sp, fontWeight = FontWeight.Normal)
                        }
                    }



                }
            }
        }
    )
}

@Preview (showSystemUi = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun HelpSupportPreview(){
    HelpSupport(rememberNavController())
}