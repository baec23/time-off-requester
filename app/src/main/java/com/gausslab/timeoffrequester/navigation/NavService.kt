package com.gausslab.timeoffrequester.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import com.baec23.ludwig.component.navbar.BottomNavigationBar
import com.gausslab.timeoffrequester.navigation.navgraph.authNavGraph
import com.gausslab.timeoffrequester.navigation.navgraph.authNavGraphRoute
import com.gausslab.timeoffrequester.navigation.navgraph.bottomNavigationItems
import com.gausslab.timeoffrequester.navigation.navgraph.mainNavGraph
import com.gausslab.timeoffrequester.navigation.navgraph.mainNavGraphRoute
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


const val rootNavGraphRoute = "root_nav_graph_route"


@ActivityScoped
class NavService(val navController: NavHostController) {
    val rootNavGraph =
        navController.createGraph(startDestination = authNavGraphRoute, route = rootNavGraphRoute) {
            authNavGraph()
            mainNavGraph()
        }

    private val _currNavDestination: MutableState<NavDestination?> = mutableStateOf(null)
    val currNavDestination: State<NavDestination?> = _currNavDestination

    private val _currParentGraphRoute: MutableState<String?> = mutableStateOf(null)
    val currParentGraphRoute: State<String?> = _currParentGraphRoute

    private val _currNavGraph: MutableState<NavGraph?> = mutableStateOf(rootNavGraph)
    val currNavGraph: State<NavGraph?> = _currNavGraph

    private val _currNavScreenRoute: MutableState<String?> = mutableStateOf(null)
    val currNavScreenRoute: State<String?> = _currNavScreenRoute
    fun navigateUp() {
        navController.navigateUp()
    }
    init {
        MainScope().launch {
            navController.currentBackStackEntryFlow.collect {
                _currNavGraph.value = it.destination.parent
                _currNavDestination.value = it.destination
                _currParentGraphRoute.value = it.destination.parent?.route
                _currNavScreenRoute.value = it.destination.route
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navService: NavService,
) {
    val currNavGraph by navService.currNavGraph
    val shouldShowBottomNavBar by remember { derivedStateOf { currNavGraph?.route == mainNavGraphRoute } }

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(visible = shouldShowBottomNavBar) {
                BottomNavigationBar(
                    modifier = Modifier.height(50.dp),
                    items = bottomNavigationItems,
                    currNavScreenRoute = navService.currNavScreenRoute.value,
                    backgroundColor = Color.Unspecified,
                    onBottomNavigationItemPressed = { navService.navController.navigate(it.route) }
                )
            }
        }) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            NavHost(
                navController = navService.navController,
                graph = navService.rootNavGraph
            )
        }
    }
}