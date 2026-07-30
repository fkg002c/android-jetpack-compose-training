package com.fkg002c.modaldrawer.ui.element

import androidx.compose.foundation.border
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HoverCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)) // Розовая рамка со скрина
            .padding(10.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
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
