package com.gausslab.timeoffrequester.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.gausslab.timeoffrequester.ui.comp.BottomNavBar
import com.gausslab.timeoffrequester.ui.comp.bottomNavBarItems
import com.gausslab.timeoffrequester.ui.login.loginScreen
import com.gausslab.timeoffrequester.ui.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.main.mainScreen
import com.gausslab.timeoffrequester.ui.requestlist.requestListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val currNavScreenRoute by viewModel.currNavScreenRoute

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            //TODO: TopBar
        },
        bottomBar = {
            BottomNavBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                currNavScreenRoute = currNavScreenRoute,
                items = bottomNavBarItems,
                onBottomNavBarButtonPressed = {
                    viewModel.onEvent(AppUiEvent.BottomNavBarButtonPressed(it))
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            NavHost(
                navController = viewModel.navController,
                startDestination = loginScreenRoute
            ) {
                loginScreen()
                mainScreen()
                requestListScreen()
            }
        }
    }
}
