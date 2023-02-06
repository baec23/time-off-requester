package com.gausslab.timeoffrequester.ui.screen.requestlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.core.fadingLazy.FadingLazyColumn

const val requestListScreenRoute = "request_list_screen_route"

fun NavGraphBuilder.requestListScreen() {
    composable(route = requestListScreenRoute) {
        RequestListScreen()
    }
}

fun NavController.navigateToRequestListScreen(navOptions: NavOptions? = null) {
    this.navigate(route = requestListScreenRoute, navOptions = navOptions)
}

@Composable
fun RequestListScreen(
    viewModel: RequestListViewModel = hiltViewModel()
) {
    val myTimeOFfRequestList by viewModel.myTimeOffRequestList.collectAsState()

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, bottom = 16.dp)
    ) {
        DisplaySection(headerText = "연차 사용내역") {
            FadingLazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
                items(
                    myTimeOFfRequestList.size
                ){
                    val timeOffRequest = myTimeOFfRequestList[it]

                }
            }
        }
    }
}

@Composable
fun RequestCard(
    modifier: Modifier = Modifier,
    startDate: String,
    endDate: String,

) {

}