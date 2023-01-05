package com.gausslab.timeoffrequester.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.gausslab.timeoffrequester.ui.login.loginScreen
import com.gausslab.timeoffrequester.ui.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.main.mainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appViewModel: AppViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            //TODO: TopBar
        },
        bottomBar = {
            //TODO: BottomBar
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            NavHost(navController = appViewModel.navController, startDestination = loginScreenRoute) {
                loginScreen()
                mainScreen()
            }
        }
    }
}