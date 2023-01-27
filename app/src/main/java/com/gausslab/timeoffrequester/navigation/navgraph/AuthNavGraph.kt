package com.gausslab.timeoffrequester.navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.gausslab.timeoffrequester.navigation.NavService
import com.gausslab.timeoffrequester.ui.screen.login.loginScreen
import com.gausslab.timeoffrequester.ui.screen.login.loginScreenRoute


const val authNavGraphRoute = "auth_nav_graph_route"

fun NavGraphBuilder.authNavGraph() {
    navigation(startDestination = loginScreenRoute, route = authNavGraphRoute) {
        loginScreen()
    }
}

fun NavService.navigateToAuthNavGraph() {
    navController.navigate(
        authNavGraphRoute,
        navOptions = navOptions {
            popUpTo(navController.graph.id){
                inclusive = true
            }
        })
}