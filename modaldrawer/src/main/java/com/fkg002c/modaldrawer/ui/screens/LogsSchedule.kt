package com.fkg002c.modaldrawer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Preview(showSystemUi = true)
@Composable
fun LogsScheduleScreen(
    padding: PaddingValues = PaddingValues(top = topPadding(), bottom = bottomPadding())
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
        ) {
            repeat(50) { index ->
                item {
                    Text(
                        text = "Logs Schedule ${index + 1}",
                        fontSize = 36.sp,
//                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun topPadding(): Dp {
    val density = LocalDensity.current

    // 1. Get Status Bar Height in Dp
    val statusBarHeightDp = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }

    // 2. Get TopAppBar Height Token (e.g., Small/Standard is 64.dp)
    val appBarHeightDp = TopAppBarDefaults.TopAppBarExpandedHeight

    return statusBarHeightDp + appBarHeightDp
}

@Composable
fun bottomPadding(): Dp {
    val density = LocalDensity.current

    // 1. Get Navigation Bar Height in Dp
    val navigationBarHeightDp = with(density) {
        WindowInsets.navigationBars.getTop(this).toDp()
    }

    return navigationBarHeightDp
}