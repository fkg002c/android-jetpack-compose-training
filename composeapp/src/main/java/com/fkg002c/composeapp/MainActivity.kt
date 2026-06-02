package com.fkg002c.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fkg002c.composeapp.ui.theme.mainBlackColor
import com.fkg002c.composeapp.ui.theme.subtitleGrayColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                content = { padding: PaddingValues ->
                    Column(
                        modifier = Modifier
                            // .fillMax...
                            // .background
                            // . clip
                            // .alpha
                            // .rotate
                            .padding(padding)
//                            .background(Color.LightGray)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        StudyAppHeader()
                        Spacer(modifier = Modifier.height(30.dp))
                        MainNavButtons()
                        Spacer(modifier = Modifier.height(30.dp))
                        StartImageButton()
                    }
                }
            )
        }
    }
}

@Composable
fun StudyAppHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AndroidSprint",
            fontSize = 28.sp,
            color = mainBlackColor,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
        )
        Text(
            text = "Learning Kotlin & Android through practice",
            fontSize = 16.sp,
            color = subtitleGrayColor,
            fontFamily = FontFamily(Font(R.font.gilroy_medium)),
        )
    }
}

@Composable
fun MainNavButtons() {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Button(
            onClick = {},
            shape = RoundedCornerShape(13.dp),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                text = "Lessons",
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
            )
        }
        Button(
            onClick = {},
            shape = RoundedCornerShape(13.dp),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                text = "Tests",
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
            )
        }
        Button(
            onClick = {},
            shape = RoundedCornerShape(13.dp),
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                text = "Practice",
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
            )
        }
    }
}

@Composable
fun StartImageButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = "",
            painter = painterResource(R.drawable.rocket2),
            modifier = Modifier
                .size(120.dp)
                .shadow(3.dp, CircleShape) // order!
                .clip(CircleShape)                   // order!
                .clickable(                          // order!
                    onClick = {},
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
        Text(
            text = "Start onboarding",
            fontSize = 16.sp,
            color = subtitleGrayColor,
            fontFamily = FontFamily(Font(R.font.gilroy_medium)),
        )

    }
}

@Composable
@Preview(showBackground = true)
private fun StudyAppHeaderPreview() {
    StudyAppHeader()
}

@Composable
@Preview
fun MainNavButtonsPreview() {
    MainNavButtons()
}

@Composable
@Preview
fun StartImageButtonPreview(modifier: Modifier = Modifier) {
    StartImageButton()
}
