package com.gausslab.timeoffrequester.ui.main

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
import com.gausslab.timeoffrequester.ui.login.LoginScreen

const val mainScreenRoute = "main_screen_route"

fun NavGraphBuilder.mainScreen() {
    composable(route = mainScreenRoute) {
        MainScreen()
    }
}

fun NavController.navigateToMainScreen(navOptions: NavOptions? = null){
    this.navigate(route = mainScreenRoute, navOptions = navOptions)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Button(onClick = { viewModel.onEvent(MainUiEvent.GoToLoginScreenPressed) }) {
            Text("Go to Login Screen")
        }
    }

}