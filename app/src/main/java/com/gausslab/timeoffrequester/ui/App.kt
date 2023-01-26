package com.gausslab.timeoffrequester.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.gausslab.timeoffrequester.ui.changepassword.changePasswordScreen
import com.gausslab.timeoffrequester.ui.changepassword.changePasswordScreenRoute
import com.gausslab.timeoffrequester.ui.comp.BottomNavBar
import com.gausslab.timeoffrequester.ui.comp.bottomNavBarItems
import com.gausslab.timeoffrequester.ui.findpassword.findPasswordScreen
import com.gausslab.timeoffrequester.ui.findpassword.findPasswordScreenRoute
import com.gausslab.timeoffrequester.ui.login.loginScreen
import com.gausslab.timeoffrequester.ui.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.main.mainScreen
import com.gausslab.timeoffrequester.ui.myprofile.myProfileScreen
import com.gausslab.timeoffrequester.ui.myprofile.myProfileScreenRoute
import com.gausslab.timeoffrequester.ui.myprofile.navigateToMyProfileScreen
import com.gausslab.timeoffrequester.ui.myprofiledetails.myProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.myprofiledetails.myProfileDetailsScreenRoute
import com.gausslab.timeoffrequester.ui.requestdetails.requestDetailsScreen
import com.gausslab.timeoffrequester.ui.requestdetails.requestDetailsScreenRoute
import com.gausslab.timeoffrequester.ui.requestlist.navigateToRequestListScreen
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
            if (currNavScreenRoute != loginScreenRoute) {
//                TopBar(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    items = topBarItem,
//                    currNavScreenRoute = currNavScreenRoute,
//                    onTopBarButtonPressed = {
//                        viewModel.onEvent(AppUiEvent.TopBarButtonPressed(it))
//                    }
//                )
                TopAppBar(
                    modifier = Modifier.height(50.dp),
                    title = {
                        when (currNavScreenRoute) {
                            "$requestDetailsScreenRoute/{timeOffRequestId}" -> Text(
                                modifier = Modifier.padding(
                                    top = 8.dp
                                ), text = "연차내역 상세보기"
                            )

                            "$myProfileDetailsScreenRoute/{userId}" -> Text(
                                modifier = Modifier.padding(
                                    top = 8.dp
                                ), text = "내 정보 상세보기"
                            )

                            changePasswordScreenRoute -> Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "비밀번호 변경"
                            )
                        }
                    },
                    navigationIcon = {
                        if (currNavScreenRoute == "$requestDetailsScreenRoute/{timeOffRequestId}" || currNavScreenRoute == "$myProfileDetailsScreenRoute/{userId}" || currNavScreenRoute == changePasswordScreenRoute) {
                            IconButton(
                                onClick = {
                                    when (currNavScreenRoute) {
                                        "$requestDetailsScreenRoute/{timeOffRequestId}" -> viewModel.navController.navigateToRequestListScreen()
                                        "$myProfileDetailsScreenRoute/{userId}" -> viewModel.navController.navigateToMyProfileScreen()
                                        changePasswordScreenRoute -> viewModel.navController.navigateToMyProfileScreen()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.ArrowBack, null)
                            }
                        }
                    },
                    actions = {
                        if (currNavScreenRoute == myProfileScreenRoute) {
                            IconButton(onClick = { viewModel.onEvent(AppUiEvent.LogoutPressed) }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Logout"
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (!(currNavScreenRoute == loginScreenRoute || currNavScreenRoute == findPasswordScreenRoute)
            ) {
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

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            NavHost(
                navController = viewModel.navController,
                startDestination = loginScreenRoute
            ) {
                loginScreen()
                findPasswordScreen()
                mainScreen()
                requestListScreen()
                requestDetailsScreen()
                myProfileScreen()
                myProfileDetailsScreen()
                changePasswordScreen()
            }
        }
    }
}

