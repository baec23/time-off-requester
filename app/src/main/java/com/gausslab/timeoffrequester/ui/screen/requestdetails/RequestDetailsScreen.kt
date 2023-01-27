package com.gausslab.timeoffrequester.ui.screen.requestdetails

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
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.baec23.ludwig.component.section.ExpandableDisplaySection
import com.gausslab.timeoffrequester.model.toKorean
import com.gausslab.timeoffrequester.ui.screen.DetailsScreen

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
        timeOffRequestId?.let {
            val viewModel: RequestDetailsViewModel = hiltViewModel()
            viewModel.setCurrTimeOffRequest(timeOffRequestId = timeOffRequestId.toString())
            RequestDetailsScreen(viewModel = viewModel, timeOffRequestId = timeOffRequestId)
        }
    }
}

fun NavController.navigateToRequestDetailsScreen(
    timeOffRequestId: Int,
    navOptions: NavOptions? = null
) {
    val routeWithArguments = "$requestDetailsScreenRoute/$timeOffRequestId"
    this.navigate(route = routeWithArguments, navOptions = navOptions)
}

@Composable
fun RequestDetailsScreen(
    viewModel: RequestDetailsViewModel = hiltViewModel(),
    timeOffRequestId: Int,
) {
    val currTimeOffRequest by viewModel.currTimeOffRequest.collectAsState()
    val isAdditionalInformationExpanded by viewModel.expandableSessionState
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
                DateTimeSection(
                    startDate = currTimeOffRequest.startDate,
                    startTime = currTimeOffRequest.startTime,
                    endDate = currTimeOffRequest.endDate,
                    endTime = currTimeOffRequest.endTime,
                )
                TimeOffRequestReasonSection(
                    content = currTimeOffRequest.requestReason,
                    title = "신청사유"
                )
                TimeOffRequestReasonSection(
                    content = currTimeOffRequest.timeOffRequestType.toKorean(),
                    title = "휴가구분"
                )
                ExpandableDisplaySection(
                    isExpanded = isAdditionalInformationExpanded,
                    headerText = "추가 세부 입력",
                    onExpand = { viewModel.onEvent(RequestDetailsUiEvent.ExpandableSessionPressed) }
                ) {
                    LabelValueSection(
                        sectionName = "경조구분: ",
                        content = currTimeOffRequest.timeOffRequestTypeDetails.toKorean()
                    )

                    LabelValueSection(
                        sectionName = "대리업무자: ",
                        content = currTimeOffRequest.agentName!!
                    )
                    LabelValueSection(
                        sectionName = "비상연락망: ",
                        content = currTimeOffRequest.emergencyNumber!!
                    )
                }
            }
        }
    }

}

@Composable
fun DateTimeSection(
    modifier: Modifier = Modifier,
    startDate: String,
    startTime: String,
    endDate: String,
    endTime: String,
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
            Text(text = startDate)
            Image(
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Icon"
            )
            Text(text = startTime)
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
            Text(text = endDate)
            Image(
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Icon"
            )
            Text(text = endTime)
        }
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
    content: String,
    title: String
) {
    DisplaySection(
        modifier = modifier,
        headerText = title
    ) {
        Text(modifier = Modifier.padding(start = 3.dp), text = content)
    }
}
