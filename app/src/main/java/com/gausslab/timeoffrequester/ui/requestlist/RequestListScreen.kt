package com.gausslab.timeoffrequester.ui.requestlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.LabelledValueButton
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.core.fadinglazy.FadingLazyColumn


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
                        count = 50
                    ) {
                        LabelledValueButton(
                            label =it.toString(),
                            value ="상태(승인대기중)",
                            onClick = { viewModel.onEvent(RequestListUiEvent.RequestDetailClicked)}
                        )
                    }
                }
            }


        }
    }
}