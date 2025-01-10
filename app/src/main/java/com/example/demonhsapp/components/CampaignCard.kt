package com.example.demonhsapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demonhsapp.R
import com.example.demonhsapp.ui.theme.nhsDarkBlue

@Preview (showBackground = true, backgroundColor = 0xFFF0F4F5)
@Composable
fun CampaignCard() {
    Column (Modifier.padding(bottom = 16.dp)) {
        Card(onClick = {/*TODO*/ }, modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(nhsDarkBlue)) {
            Column() {
                Image(painterResource(R.drawable.campaign_card_image_3), contentDescription = null, contentScale = ContentScale.Fit)
                Column(modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)) {
                    Text("Help change the NHS", modifier = Modifier.padding(bottom = 8.dp), fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Have your say on making the NHS fit for the future", fontSize = 16.sp, color = Color.White)
                }

            }
        }
    }
}