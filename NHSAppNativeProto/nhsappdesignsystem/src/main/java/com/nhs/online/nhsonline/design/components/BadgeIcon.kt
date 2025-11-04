package com.nhs.online.nhsonline.design.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BadgeIcon(
    imageVector: ImageVector,
    badgeCount: Int = 0,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    BadgedBox(
        badge = {
            if (badgeCount > 0) {
                Badge {
                    Text(badgeCount.toString())
                }
            }
        }
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun BadgeIcon(
    painter: Painter,
    badgeCount: Int,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    BadgedBox(
        badge = {
            if (badgeCount > 0) {
                Badge {
                    Text(badgeCount.toString())
                }
            }
        }
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}