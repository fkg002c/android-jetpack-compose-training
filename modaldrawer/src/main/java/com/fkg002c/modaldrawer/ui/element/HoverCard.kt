package com.fkg002c.modaldrawer.ui.element

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HoverCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val isDarkTheme = isSystemInDarkTheme()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 12.dp,
        animationSpec = tween(durationMillis = 150)
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.02f,
        animationSpec = tween(durationMillis = 150)
    )

    // Мягкое неоновое свечение в темной теме (берем основной розовый цвет)
    val shadowColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    } else {
        Color.Black.copy(alpha = 0.2f)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Важно: большой паддинг, чтобы свечение не обрезалось экраном
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            }
            // 1. Сначала рисуем тень (она выходит наружу)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(10.dp),
                clip = false, // Тень не должна обрезаться!
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            // 2. Только потом клипаем внутренний контент карточки
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { },
        // Возвращаем ваш исходный цвет фона
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Preview
@Composable
fun HowerCardPreview() {
    HoverCard("Title") {
        Text("Consider enabling configuration cache to speed up this build: https://docs.gradle.org/9.4.1/userguide/configuration_cache_enabling.html")
        Button(
            onClick = {}
        ) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Sopping card")
            Text("Shopping card")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = true,
                    onValueChange = {},
                    role = Role.Switch
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text labels column
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Some switch control element",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            // Switch component (onCheckedChange is null because the Row handles the toggle)
            Switch(
                checked = true,
                onCheckedChange = {}
            )
        }

    }
}
