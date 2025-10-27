package com.nhs.online.nhsonline.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nhs.online.nhsonline.design.theme.NHSAppTheme

@Composable
fun NHSRowItem(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(0.dp),
    rowItem: Item,
    onClick: (() -> Unit)? = null
) {

    Row(
        modifier = modifier
            .fillMaxWidth().background(color = Color.White, shape = shape)
            .clickable(onClick = { onClick?.invoke() })
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        rowItem.leadingIcon?.let {
            if(rowItem.notificationCount > 0){
                BadgeIcon(it, rowItem.notificationCount)
            }else{
                Icon(it, contentDescription = null)
            }
        }

        Column(Modifier.weight(1f).padding(start = 8.dp)) {
            Text(
                text = "Sub" + rowItem.title,
            )
            Text(
                text = rowItem.title,
            )
        }

        Icon(
            imageVector = rowItem.trailingIcon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}


data class Item(
    val title: String,
    val isBrowserJourney: Boolean = false,
    val route: String? = null,
    val trailingIcon: ImageVector,
    val leadingIcon: ImageVector? = null,
    val notificationCount: Int = 0,
)

@Preview
@Composable
private fun NHSRowItemPreview(){
    val item = Item(
        title = "Check if you need urgent medical help using 111 online",
        route = "https://111.nhs.uk",
        isBrowserJourney = true,
        notificationCount = 2,
        leadingIcon = Icons.Outlined.SupervisedUserCircle,
        trailingIcon = Icons.AutoMirrored.Outlined.OpenInNew
    )
//    val shape = when {
//        rowItem.isFirstItem && rowItem.isLastItem -> RoundedCornerShape(12.dp) // single item
//        rowItem.isFirstItem -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
//        rowItem.isLastItem -> RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
//        else -> RoundedCornerShape(0.dp)
//    }

    NHSAppTheme {
        NHSRowItem(rowItem = item, shape = RoundedCornerShape(12.dp)){}
    }
}