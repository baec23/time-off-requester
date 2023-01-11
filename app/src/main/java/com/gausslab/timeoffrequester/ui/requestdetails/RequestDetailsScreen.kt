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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gausslab.timeoffrequester.model.TimeOffRequest


const val requestDetailsScreenRoute = "requestDetails_screen_route"

fun NavGraphBuilder.requestDetailsScreen() {
    composable(route = requestDetailsScreenRoute) {
        RequestDetailsScreen()
    }
}

fun NavController.navigateToRequestDetailsScreen(navOptions: NavOptions? = null) {
    this.navigate(route = requestDetailsScreenRoute, navOptions = navOptions)
}

@Composable
fun RequestDetailsScreen(
    viewModel: RequestDetailsViewModel = hiltViewModel()
) {
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
                date = "220101",
                time = "0900"
            )
            DateTimeSection(
                sectionName = "종료",
                date = "220101",
                time = "1830"
            )
            LabelValueSection(
                sectionName = "휴가구분: ",
                content = "연차휴가"
            )
            LabelValueSection(
                sectionName = "경조구분: ",
                content = "기타"
            )
            TimeOffRequestReasonSection(
                sectionName = "신청사유: ",
                requestReason = "개인사유"
            )
            LabelValueSection(
                sectionName = "대리업무자: ",
                content = "홍길동"
            )
            LabelValueSection(
                sectionName = "비상연락망: ",
                content = "01012345678"
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