package com.gausslab.timeoffrequester.ui.requestlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.baec23.ludwig.component.button.LabelledValueButton
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.core.fadingLazy.FadingLazyColumn


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
fun RequestListScreen(
    viewModel: RequestListViewModel = hiltViewModel()
) {
    val myTimeOffRequests by viewModel.myTimeOffRequests.collectAsState()

    Surface(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column() {
            DisplaySection(headerText = "연차사용내역") {
                FadingLazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    items(
                        myTimeOffRequests.size
                    ) {
                        val timeOffRequest = myTimeOffRequests[it]
                        LabelledValueButton(
                            label = timeOffRequest.startDate + " - " + timeOffRequest.endDate,
                            value = timeOffRequest.status,
                            onClick = {
                                viewModel.onEvent(
                                    RequestListUiEvent.RequestDetailClicked(
                                        timeOffRequest
                                    )
                                )
                            }
                        )
                    }
                }
            }


        }
    }
}