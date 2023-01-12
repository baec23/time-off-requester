package com.gausslab.timeoffrequester.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.inputfield.InputValidator

const val loginScreenRoute = "login_screen_route"

fun NavGraphBuilder.loginScreen() {
    composable(route = loginScreenRoute) {
        LoginScreen()
    }
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(route = loginScreenRoute, navOptions = navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val formState by viewModel.formState
    val userEmail = formState.userEmail
    val password = formState.password
    val isFormValid by viewModel.isFormValid
    val autoLoginChecked by viewModel.autoLoginChecked

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            InputField(
                modifier = Modifier,
                value = userEmail,
                onValueChange = { viewModel.onEvent(LoginUiEvent.UserEmailChanged(it)) },
                label = "Email",
                placeholder = "Email",
                singleLine = true,
                inputValidator = InputValidator { it.isLetterOrDigit() || it == '@'|| it=='.'  }
            )

            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                modifier = Modifier,
                value = password,
                onValueChange = { viewModel.onEvent(LoginUiEvent.PasswordChanged(it)) },
                label = "Password",
                placeholder = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                inputValidator = InputValidator.TextNoSpaces
            )

            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = autoLoginChecked,
                    onCheckedChange = { viewModel.onEvent(LoginUiEvent.AutoLoginPressed) },
                )
                Text(text = "자동 로그인")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "password찾기")
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.scale(1.2f),
                onClick = { viewModel.onEvent(LoginUiEvent.LoginButtonPressed) },
                enabled = isFormValid,
            ) {
                Text("Login")
            }

        }
    }

}