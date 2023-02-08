package com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.StatefulButton
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.section.DisplaySection
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
import com.gausslab.timeoffrequester.ui.screen.DetailsScreen
import com.gausslab.timeoffrequester.util.DetailsScreenRoute
import com.gausslab.timeoffrequester.util.toKorean

val myDetailsInfoEditScreenRoute = DetailsScreenRoute.my_details_info_edit_screen_route

fun NavGraphBuilder.myDetailsInfoEditScreen() {
    composable(route = myDetailsInfoEditScreenRoute.toKorean()) {
        MyDetailsInfoEditScreen()
    }
}

fun NavController.navigateToMyDetailsInfoEditeScreen(navOptions: NavOptions? =null){
    this.navigate(route = myDetailsInfoEditScreenRoute.toKorean(), navOptions = navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDetailsInfoEditScreen(
    viewModel: MyDetailsInfoEditViewModel = hiltViewModel()
) {
    val currUser = viewModel.currUser

    val reason by viewModel.reasonText.collectAsState()
    val agentName by viewModel.agentName.collectAsState()
    val emergencyNumber by viewModel.emergencyNumber.collectAsState()
    val timeOffRequestTypeDetail by viewModel.timeOffRequestTypeDetails.collectAsState()

    val isBusy by viewModel.isBusy.collectAsState()

    val isTimeOffRequestTypeDetailsExpanded by viewModel.timeOffRequestTypeDetailsExpanded

    val items = listOf(
        TimeOffRequestTypeDetail.FUNERAL_LEAVE,
        TimeOffRequestTypeDetail.MARRIAGE_LEAVE,
        TimeOffRequestTypeDetail.OTHER
    )

    AnimatedVisibility(visible = isBusy) {
        AlertDialog(onDismissRequest = { }) {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("작업중...", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }

    DetailsScreen {
        DisplaySection(headerText = "내 추가 정보 수정") {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "신청 사유", color = Color.DarkGray)
                Spacer(modifier = Modifier.width(10.dp))
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = reason,
                    onValueChange = { viewModel.onEvent(MyDetailsInfoEditUiEvent.OnReasonChanged(it)) },
                    minLines = 1,
                    maxLines = 1,
                    placeholder = "신청 사유를 적어주세요 예) 개인사유"
                )
            }
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier =Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "대리업무자", color = Color.DarkGray)
                Spacer(modifier = Modifier.width(10.dp))
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = agentName,
                    onValueChange = { viewModel.onEvent(MyDetailsInfoEditUiEvent.AgentNameChanged(it)) },
                    minLines = 1,
                    maxLines = 1,
                    placeholder = "대리업무자를 적어주세요 예) 홍길동"
                )
            }
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "비상연락망", color = Color.DarkGray)
                Spacer(modifier = Modifier.width(10.dp))
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = emergencyNumber,
                    onValueChange = { viewModel.onEvent(MyDetailsInfoEditUiEvent.EmergencyNumberChanged(it)) },
                    minLines = 1,
                    maxLines = 1,
                    placeholder = "비상연락망을 적어주세요 예)01012341234"
                )
            }
            Row(modifier = Modifier.height(30.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "경조 구분", color = Color.DarkGray)
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        ),
                ) {
                    var selectedIndex by remember { mutableStateOf(2) }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, top = 3.dp, bottom = 3.dp)
                            .clickable(onClick = {
                                viewModel.onEvent(
                                    MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetailsExpanded(
                                        true
                                    )
                                )
                            }),
                        text = items[selectedIndex].toKorean(),
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown Icon"
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary),
                        expanded = isTimeOffRequestTypeDetailsExpanded,
                        onDismissRequest = {
                            viewModel.onEvent(
                                MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetailsExpanded(
                                    false
                                )
                            )
                        },
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                                text = {
                                    Text(text = s.toKorean())
                                },
                                onClick = {
                                    selectedIndex = index
                                    viewModel.onEvent(
                                        MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetailsExpanded(
                                            false
                                        )
                                    )
                                    viewModel.onEvent(
                                        MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetails(
                                            items[selectedIndex]
                                        )
                                    )
                                },
                                colors = MenuDefaults.itemColors(textColor = Color.Black),
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        StatefulButton(
            modifier = Modifier.fillMaxWidth(),
            text = "저장"
        ) {
            viewModel.onEvent(MyDetailsInfoEditUiEvent.OnSubmitPressed)
        }
    }
}