package com.prototype.nhsappdv1.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prototype.nhsappdv1.ui.theme.nhsBlack
import com.prototype.nhsappdv1.ui.theme.nhsBlue


@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun ServicesCard() {
    Column (Modifier.padding(bottom = 16.dp)) {
        Card(onClick = {/*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)) {

                Column(modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Outlined.LocalHospital, contentDescription = null, modifier = Modifier.padding(bottom = 8.dp), tint = nhsBlue)
                    Text("Access services", modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = nhsBlack)
                    Text("Find available GP appointments and other available services", modifier = Modifier.padding(bottom = 8.dp),  textAlign = TextAlign.Center, fontSize = 16.sp, color = nhsBlack)
                    ButtonSecondary("Explore services") { }
                }


        }
    }
}