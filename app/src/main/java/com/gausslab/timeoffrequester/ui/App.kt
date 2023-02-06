package com.gausslab.timeoffrequester.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.baec23.ludwig.component.navbar.BottomNavigationBar
import com.gausslab.timeoffrequester.ui.comp.BottomNavBar
import com.gausslab.timeoffrequester.ui.comp.bottomNavBarItem
import com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit.myDetailsInfoEditScreen
import com.gausslab.timeoffrequester.ui.screen.landing.landingScreen
import com.gausslab.timeoffrequester.ui.screen.landing.landingScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreen
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.myProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.requestform.requestFormScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val currNavScreenRoute by viewModel.currNavScreenRoute

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { },
        bottomBar = {
            BottomNavBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                items = bottomNavBarItem,
                currNavScreenRoute = currNavScreenRoute,
                onBottomNavBarButtonPressed ={
                    viewModel.onEvent(AppUiEvent.BottomNavBarButtonPressed(it))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            NavHost(
                navController = viewModel.navController,
                startDestination = landingScreenRoute
            ) {
                landingScreen()
                requestFormScreen()
                myProfileScreen()
                myProfileDetailsScreen()
                myDetailsInfoEditScreen()
            }
        }
    }
}

