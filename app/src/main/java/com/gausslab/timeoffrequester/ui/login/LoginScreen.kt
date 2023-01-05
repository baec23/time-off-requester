package com.gausslab.timeoffrequester.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val loginScreenRoute = "login_screen_route"

fun NavGraphBuilder.loginScreen() {
    composable(route = loginScreenRoute) {
        LoginScreen()
    }
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(route = loginScreenRoute, navOptions = navOptions)
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(onClick = { viewModel.onEvent(LoginUiEvent.GoToMainScreenPressed) }) {
            Text("Go to main screen")
        }
    }
}