package com.gausslab.timeoffrequester.ui.requestdetails

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val requestDetailsScreenRoute = "requestDetails_screen_route"

fun NavGraphBuilder.requestDetailsScreen() {
    composable(
        route = "$requestDetailsScreenRoute/{timeOffRequestId}", arguments = listOf(
            navArgument("timeOffRequestId") {
                type = NavType.IntType
                nullable = false
            }
        )
    ) {
        val timeOffRequestId = it.arguments?.getInt("timeOffRequestId")
        timeOffRequestId?.let { RequestDetailsScreen(timeOffRequestId = timeOffRequestId) }
    }
}

fun NavController.navigateToRequestDetailsScreen(timeOffRequestId: Int, navOptions: NavOptions? = null) {
    val routeWithArguments = "$requestDetailsScreenRoute/$timeOffRequestId"
    this.navigate(route = routeWithArguments, navOptions = navOptions)
}

@Composable
fun RequestDetailsScreen(
    viewModel: RequestDetailsViewModel = hiltViewModel(),
    timeOffRequestId: Int,
) {
    val currTimeOffRequest by viewModel.currTimeOffRequest.collectAsState()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {

            RequestStatusSection()
            Spacer(modifier = Modifier.height(20.dp))
            DateTimeSection(
                sectionName = "시작",
                date = currTimeOffRequest.startDate,
                time = currTimeOffRequest.startTime
            )
            DateTimeSection(
                sectionName = "종료",
                date = currTimeOffRequest.endDate,
                time = currTimeOffRequest.endTime
            )
            LabelValueSection(
                sectionName = "휴가구분: ",
                content = currTimeOffRequest.timeOffRequestType.toString()
            )
            LabelValueSection(
                sectionName = "경조구분: ",
                content = currTimeOffRequest.timeOffRequestTypeDetails.toString()
            )
            TimeOffRequestReasonSection(
                sectionName = "신청사유: ",
                requestReason = currTimeOffRequest.requestReason
            )
            LabelValueSection(
                sectionName = "대리업무자: ",
                content = currTimeOffRequest.agentName!!
            )
            LabelValueSection(
                sectionName = "비상연락망: ",
                content = currTimeOffRequest.emergencyNumber!!
            )
            ConfirmButton(
                onUiEvent = { viewModel.onEvent(it) }
            )
        }
    }
}

@Composable
fun RequestStatusSection(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.DarkGray, shape = RectangleShape),
    ) {
        Text(modifier = Modifier.padding(5.dp), text = "상태 : ${"승인대기중"}")
    }
}

@Composable
fun DateTimeSection(
    modifier: Modifier = Modifier,
    sectionName: String,
    date: String,
    time: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = "${sectionName}날짜: ")
        Text(text = date)
        Spacer(modifier = Modifier.width(40.dp))
        Text(text = "${sectionName}시간: ")
        Text(text = time)
    }
}

@Composable
fun LabelValueSection(
    modifier: Modifier = Modifier,
    sectionName: String,
    content: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = sectionName)
        Text(text = content)
    }
}

@Composable
fun TimeOffRequestReasonSection(
    modifier: Modifier = Modifier,
    sectionName: String,
    requestReason: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = sectionName)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.DarkGray, shape = RectangleShape)
        ) {
            Text(modifier = Modifier.padding(start = 3.dp), text = requestReason)
        }
    }
}

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    onUiEvent: (RequestDetailsUiEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onUiEvent(RequestDetailsUiEvent.ConfirmButtonPressed) }
        ) {
            Text(text = "CONFIRM!")
        }
    }
}