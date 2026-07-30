package com.fkg002c.modaldrawer.ui.element

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
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
    content: @Composable ColumnScope.(isParentPressed: Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val isDarkTheme = isSystemInDarkTheme()

    // 1. Анимации для темной темы (пульсация)
    val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAlpha"
    )

    // 2. Анимация для светлой темы (плавное затухание при нажатии)
    val lightBorderAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.3f else 1.0f,
        animationSpec = tween(durationMillis = 150)
    )

    // 3. Динамический выбор цвета и прозрачности рамки
    val borderColor = if (isDarkTheme) {
        val finalAlpha = if (isPressed) 0.2f else pulseAlpha
        MaterialTheme.colorScheme.primary.copy(alpha = finalAlpha)
    } else {
        // Интенсивный нейтральный контур для светлой темы
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = lightBorderAlpha)
    }

    // Базовые параметры тени и масштаба
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 16.dp,
        animationSpec = tween(durationMillis = 150)
    )
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.02f,
        animationSpec = tween(durationMillis = 150)
    )
    val shadowColor = if (isDarkTheme) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.15f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            }
            .shadow(elevation = elevation, shape = RoundedCornerShape(10.dp), clip = false, ambientColor = shadowColor, spotColor = shadowColor)
            .shadow(elevation = elevation / 2, shape = RoundedCornerShape(10.dp), clip = false, ambientColor = shadowColor, spotColor = shadowColor)
            // Применяем вычисленный borderColor, который теперь работает везде
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(interactionSource = interactionSource, indication = null) { },
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content(isPressed)
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
