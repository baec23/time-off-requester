package com.gausslab.timeoffrequester.ui.screen.requestdetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baec23.ludwig.component.section.DisplaySection
import com.gausslab.timeoffrequester.ui.screen.DetailsScreen
import java.time.LocalDateTime

const val requestDetailsScreenRoute = "requestDetails_screen_route"

fun NavGraphBuilder.requestDetailsScreen() {
    composable(
        route = "$requestDetailsScreenRoute/{timeOffRequestId}", arguments = listOf(
            navArgument("timeOffRequestId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val timeOffRequestId = it.arguments?.getString("timeOffRequestId")
        timeOffRequestId?.let {
            val viewModel: RequestDetailsViewModel = hiltViewModel()
            viewModel.setCurrTimeOffRequest(timeOffRequestId = timeOffRequestId)
            RequestDetailsScreen(viewModel = viewModel, timeOffRequestId = timeOffRequestId)
        }
    }
}

fun NavController.navigateToRequestDetailsScreen(
    timeOffRequestId: String,
    navOptions: NavOptions? = null
) {
    val routeWithArguments = "$requestDetailsScreenRoute/$timeOffRequestId"
    this.navigate(route = routeWithArguments, navOptions = navOptions)
}

@Composable
fun RequestDetailsScreen(
    viewModel: RequestDetailsViewModel = hiltViewModel(),
    timeOffRequestId: String,
) {
    val currTimeOffRequest = viewModel.currTimeOffRequest.collectAsState()
    DetailsScreen {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "< 상태 : ${"승인대기중"} >",
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
//                DateTimeSection(
//                    startDateTime = currTimeOffRequest.value.first().startDateTime,
//                    endDateTime = currTimeOffRequest.value.first().endDateTime,
//                )
            }
        }
    }
}


@Composable
fun DateTimeSection(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
) {
    DisplaySection(
        modifier = modifier
            .height(135.dp),
        headerText = "날짜/시간"
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar Icon",
            )
            Text(text = startDateTime.toString())
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.End,
        ) {
            Image(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar Icon",
            )
            Text(text = endDateTime.toString())
        }
    }

}