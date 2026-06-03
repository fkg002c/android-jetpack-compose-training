package com.fkg002c.composeapp

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen() {

    var userEmail by remember { mutableStateOf("") }
    var isEmailFormatValid by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }

    val testEmail = "test@test.com"
    Spacer(Modifier.height(70.dp))
    StudyAppHeader(
        title = "Registration",
        subtitle = "Enter your email"
    )
    Spacer(Modifier.height(200.dp))
    CheckEmailField(
        email = userEmail,
        isEmailValid = isEmailFormatValid,
        onEmailChange = {
            userEmail = it
            isEmailFormatValid = if (it.isNotEmpty()) EMAIL_ADDRESS.matcher(it).matches() else true
            validationMessage = if (!isEmailFormatValid) "Invalid email address" else ""
        },
        onClearClicked = {
            userEmail = ""
            isEmailFormatValid = true
            validationMessage = ""
        }
    )
    Spacer(Modifier.height(30.dp))
    PrimaryButton(
        text = "Register",
        onRegisterClick = {
            validationMessage =
                if (userEmail.isEmpty() || !isEmailFormatValid) {
                    "Invalid email address"
                } else if (userEmail == testEmail) {
                    "Email is already registered"
                } else {
                    "Email has been registered successfully"
                }
            Log.i("!!!", "PrimaryButton: \"$it\" button has been pressed")
        }
    )
    Spacer(Modifier.height(30.dp))
    Text(
        text = validationMessage,
        style = MaterialTheme.typography.bodyLarge,
        color = if (validationMessage.contains("success")) Color.Green else Color.Red,
        modifier = Modifier.alpha(if (validationMessage.isNotEmpty() && isEmailFormatValid) 1f else 0f),
    )
}

@Composable
fun CheckEmailField(
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onClearClicked: () -> Unit,
) {
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
//            textState = it
//            errorState = if (EMAIL_ADDRESS.matcher(it).matches()) "" else "Invalid email address"
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp, 0.dp),
        shape = RoundedCornerShape(13.dp),
        textStyle = MaterialTheme.typography.headlineMedium,
        placeholder = {
            Text(
                text = "example@android.com",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray
            )
        },
        singleLine = true,
        label = {
            Text(
                text = if (isEmailValid) "Email" else "Invalid email address",
                style = MaterialTheme.typography.labelSmall
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onClearClicked()
//                    textState = ""
//                    errorState = ""

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear field icon"
                )
            }
        },
        isError = !isEmailValid && email.isNotEmpty()
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onRegisterClick: (String) -> Unit

) {
    Button(
        shape = RoundedCornerShape(13.dp),
        onClick = { onRegisterClick(text) },
        modifier = Modifier
            .height(56.dp)
            .padding(40.dp, 0.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
