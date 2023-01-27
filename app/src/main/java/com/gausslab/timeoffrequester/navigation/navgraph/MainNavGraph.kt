package com.gausslab.timeoffrequester.navigation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.baec23.ludwig.component.navbar.BottomNavigationItem
import com.gausslab.timeoffrequester.navigation.NavService
import com.gausslab.timeoffrequester.ui.screen.main.mainScreen
import com.gausslab.timeoffrequester.ui.screen.main.mainScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreen
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.myProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.requestdetails.requestDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.requestlist.requestListScreen
import com.gausslab.timeoffrequester.ui.screen.requestlist.requestListScreenRoute


const val mainNavGraphRoute = "main_nav_graph_route"

fun NavGraphBuilder.mainNavGraph() {
    navigation(startDestination = mainScreenRoute, route = mainNavGraphRoute) {
        mainScreen()
        requestListScreen()
        requestDetailsScreen()
        myProfileScreen()
        myProfileDetailsScreen()
    }
}

fun NavService.navigateToMainNavGraph() {
    navController.navigate(
        mainNavGraphRoute,
        navOptions = navOptions {
            popUpTo(navController.graph.id){
                inclusive = true
            }
        })
}


val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = mainScreenRoute,
        iconImageVector = Icons.Default.Home,
        label = "Home",
    ),
    BottomNavigationItem(
        route = requestListScreenRoute,
        iconImageVector = Icons.Default.Info,
        label = "RequestList"
    ),
    BottomNavigationItem(
        route = myProfileScreenRoute,
        iconImageVector = Icons.Default.Person,
        label = "MyProfile"
    )
)
