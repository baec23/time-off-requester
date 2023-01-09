package com.gausslab.timeoffrequester.ui.requestlist

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val requestListScreenRoute = "requestList_screen_route"

fun NavGraphBuilder.requestListScreen() {
    composable(route = requestListScreenRoute) {
        RequestListScreen()
    }
}

fun NavController.navigateToRequestListScreen(navOptions: NavOptions? = null) {
    this.navigate(route = requestListScreenRoute, navOptions = navOptions)
}

@Composable
fun RequestListScreen() {

}
