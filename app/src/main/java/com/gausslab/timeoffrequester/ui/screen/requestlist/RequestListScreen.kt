package com.gausslab.timeoffrequester.ui.screen.requestlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
    viewModel: RequestListViewModel = hiltViewModel()
) {
    val myTimeOFfRequestList by viewModel.myTimeOffRequestList.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        DisplaySection(headerText = "연차 사용내역") {
            FadingLazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                items(
                    myTimeOFfRequestList.size
                ) {
                    val timeOffRequest =
                        myTimeOFfRequestList.sortedBy { timeOffRequest2 -> timeOffRequest2.startDateTime }
                            .reversed()[it]
                    Card(
                        modifier = Modifier
                            .height(110.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        shape = RoundedCornerShape(6.dp),
                        onClick = {
                            viewModel.onEvent(
                                RequestListUiEvent.RequestDetailClicked(
                                    timeOffRequest
                                )
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = timeOffRequest.type,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Row(modifier = Modifier) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "${timeOffRequest.startDateTime.year}년 ${timeOffRequest.startDateTime.monthValue}월 ${timeOffRequest.startDateTime.dayOfMonth}일",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = if (timeOffRequest.startDateTime.minute == 0) {
                                        "${timeOffRequest.startDateTime.hour}시 0${timeOffRequest.startDateTime.minute}분"
                                    } else {
                                        "${timeOffRequest.startDateTime.hour}시 ${timeOffRequest.startDateTime.minute}분"
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "${timeOffRequest.endDateTime.year}년 ${timeOffRequest.endDateTime.monthValue}월 ${timeOffRequest.endDateTime.dayOfMonth}일",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = if (timeOffRequest.endDateTime.minute == 0) {
                                        "${timeOffRequest.endDateTime.hour}시 0${timeOffRequest.endDateTime.minute}분"
                                    } else {
                                        "${timeOffRequest.endDateTime.hour}시 ${timeOffRequest.endDateTime.minute}분"
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewCard() {
    Card(
        modifier = Modifier
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(6.dp),
//        onClick = {viewModel.onEvent(RequestListUiEvent.RequestDetailClicked(timeOffRequest))}
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = "<연차>",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = "2023.01.02 09:30", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "2023.01.02 18:30", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

