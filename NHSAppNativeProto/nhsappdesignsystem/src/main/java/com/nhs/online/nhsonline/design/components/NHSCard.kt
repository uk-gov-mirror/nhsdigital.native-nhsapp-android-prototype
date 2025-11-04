package com.nhs.online.nhsonline.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nhs.online.nhsonline.design.R
import com.nhs.online.nhsonline.design.theme.NHSAppTheme
import com.nhs.online.nhsonline.design.theme.nhsBlue
import com.nhs.online.nhsonline.design.theme.nhsGrey

@Composable
fun NHSCard(nhsCard: NHSCardUI){

    Column(modifier = Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(8.dp)).padding(16.dp)) {

        // NHS logo and welcome message
        Image(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .height(22.dp)
                .width(54.dp),
            painter = painterResource(R.drawable.nhs_logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text(nhsCard.welcomeMessage, fontSize = 18.sp)
        Text(
            nhsCard.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            Text("NHS number: ", fontSize = 18.sp, color = nhsGrey)
            Text(
                nhsCard.nhsNumber,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = nhsBlue
            )
        }
    }

}


data class NHSCardUI(val name: String, val nhsNumber: String, val welcomeMessage: String, )

@Preview
@Composable
private fun NHSCardPreview(){
    NHSAppTheme {
        NHSCard(NHSCardUI("Mary Swanson", "123 456 7890", "Good evening"))
    }
}