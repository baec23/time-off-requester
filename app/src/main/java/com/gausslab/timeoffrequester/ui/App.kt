package com.gausslab.timeoffrequester.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.gausslab.timeoffrequester.navigation.Navigation
import com.gausslab.timeoffrequester.ui.screen.login.loginScreen
import com.gausslab.timeoffrequester.ui.screen.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.screen.main.mainScreen
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreen
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofile.navigateToMyProfileScreen
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.myProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.myProfileDetailsScreenRoute
import com.gausslab.timeoffrequester.ui.screen.requestdetails.requestDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.requestdetails.requestDetailsScreenRoute
import com.gausslab.timeoffrequester.ui.screen.requestlist.navigateToRequestListScreen
import com.gausslab.timeoffrequester.ui.screen.requestlist.requestListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = hiltViewModel(),
) {
    Navigation(navService = viewModel.navService)
}

