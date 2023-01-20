package com.gausslab.timeoffrequester.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
import kotlin.system.exitProcess

const val mainScreenRoute = "main_screen_route"

fun NavGraphBuilder.mainScreen() {
    composable(route = mainScreenRoute) {
        MainScreen()
    }
}

fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(route = mainScreenRoute, navOptions = navOptions)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val formState by viewModel.formState
    val isFormValid by viewModel.isFormValid

    val startDateDialogState by viewModel.startDateDialogState
    val startDate = formState.startDate

    val startTime = formState.startTime
    val startTimeInputFieldHasError by viewModel.startTimeInputFieldHasError
    val startTimeInputFieldErrorMessage by viewModel.startTimeInputFieldErrorMessage

    val endDateDialogState by viewModel.endDateDialogState
    val endDate = formState.endDate

    val endTime = formState.endTime
    val endTimeInputFieldHasError by viewModel.endTimeInputFieldHasError
    val endTimeInputFieldErrorMessage by viewModel.endTimeInputFieldErrorMessage

    val isTimeOffRequestTypeExpanded by viewModel.timeOffRequestType
    val isTimeOffRequestTypeDetailsExpanded by viewModel.timeOffRequestTypeDetails

    val requestReason = formState.requestReason
    val agentName = formState.agentName
    val emergencyNumber = formState.emergencyNumber
    val remainingTimeOffRequest = viewModel.remainingTimeOffRequest

    var backPressedTime: Long = 0;
    BackHandler(
        enabled = true, onBack = {
            if (System.currentTimeMillis() > backPressedTime + 2000) {
                backPressedTime = System.currentTimeMillis()
            } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
                exitProcess(0)
            }
        })

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text(modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally), text = "< 남은 연차 수 : $remainingTimeOffRequest >", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(30.dp))
            TimeOffRequestForm(
                startDateDialogState = startDateDialogState,
                startDate = startDate,
                startTime = startTime,
                startTimeInputFieldHasError = startTimeInputFieldHasError,
                startTimeInputFieldErrorMessage = startTimeInputFieldErrorMessage,
                endDateDialogState = endDateDialogState,
                endDate = endDate,
                endTime = endTime,
                endTimeInputFieldHasError = endTimeInputFieldHasError,
                endTimeInputFieldErrorMessage = endTimeInputFieldErrorMessage,
                isTimeOffRequestTypeExpanded = isTimeOffRequestTypeExpanded,
                isTimeOffRequestTypeDetailsExpanded = isTimeOffRequestTypeDetailsExpanded,
                requestReason = requestReason,
                agentName = agentName,
                emergencyNumber = emergencyNumber,
                isFormValid = isFormValid,
                onUiEvent = { viewModel.onEvent(it) }
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = { viewModel.onEvent(MainUiEvent.SubmitButtonPressed) },
                enabled = isFormValid
            ) {
                Text(text = "SUBMIT!")
            }

        }
    }
}

@Composable
fun TimeOffRequestForm(
    modifier: Modifier = Modifier,
    startDateDialogState: Boolean,
    startDate: String,
    startTime: String,
    startTimeInputFieldHasError: Boolean,
    startTimeInputFieldErrorMessage: String?,
    endDateDialogState: Boolean,
    endDate: String,
    endTime: String,
    endTimeInputFieldHasError: Boolean,
    endTimeInputFieldErrorMessage: String?,
    isTimeOffRequestTypeExpanded: Boolean,
    isTimeOffRequestTypeDetailsExpanded: Boolean,
    requestReason: String,
    agentName: String,
    emergencyNumber: String,
    isFormValid: Boolean,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            StartDateTimeTextField(
                startDateDialogState = startDateDialogState,
                startDate = startDate,
                startTime = startTime,
                startTimeInputFieldHasError = startTimeInputFieldHasError,
                startTimeInputFieldErrorMessage = startTimeInputFieldErrorMessage,
                onUiEvent = onUiEvent
            )
            EndDateTimeTextField(
                endDateDialogState = endDateDialogState,
                endDate = endDate,
                endTime = endTime,
                endTimeInputFieldHasError = endTimeInputFieldHasError,
                endTimeInputFieldErrorMessage = endTimeInputFieldErrorMessage,
                onUiEvent = onUiEvent
            )
            TimeOffRequestTypeDropDownMenu(
                title = "휴가구분",
                expanded = isTimeOffRequestTypeExpanded,
                items = listOf(
                    TimeOffRequestType.ANNUAL_LEAVE,
                    TimeOffRequestType.HALF_LEAVE,
                    TimeOffRequestType.SICK_LEAVE,
                    TimeOffRequestType.MATERNITY_LEAVE,
                    TimeOffRequestType.CC_LEAVE,
                    TimeOffRequestType.MENSTRUATION_LEAVE,
                    TimeOffRequestType.PATERNITY_LEAVE,
                    TimeOffRequestType.PUBLIC_LEAVE
                ),
                onUiEvent = onUiEvent
            )
            TimeOffRequestTypeDetailsDropDownMenu(
                title = "경조구분",
                expanded = isTimeOffRequestTypeDetailsExpanded,
                items = listOf(
                    TimeOffRequestTypeDetail.FUNERAL_LEAVE,
                    TimeOffRequestTypeDetail.MARRIAGE_LEAVE,
                    TimeOffRequestTypeDetail.OTHER
                ),
                onUiEvent = onUiEvent
            )
            TimeOffRequestReasonBox(
                title = "신청사유",
                requestReason = requestReason,
                onUiEvent = onUiEvent
            )
            AgentNameBox(
                title = "대리업무자",
                agentName = agentName,
                onUiEvent = onUiEvent
            )
            EmergencyNumberBox(
                title = "비상연락망",
                emergencyNumber = emergencyNumber,
                onUiEvent = onUiEvent
            )
        }
    }
}

@Composable
fun StartDateTimeTextField(
    modifier: Modifier = Modifier,
    startDateDialogState: Boolean,
    startDate: String,
    startTime: String,
    startTimeInputFieldHasError: Boolean,
    startTimeInputFieldErrorMessage: String?,
    onUiEvent: (MainUiEvent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(text = "시작날짜: ")
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onUiEvent(MainUiEvent.StartDateDialogPressed)
                },
            text = if (startDate == "") {
                " -> 선택(클릭)"
            } else {
                startDate
            },
            color = if (startDate == "") {
                Color.Blue
            } else {
                Color.Black
            }
        )

        if (startDateDialogState) {
            Dialog(onDismissRequest = { }) {
                com.baec23.ludwig.component.datepicker.DatePicker(
                    onDateSelectionFinalized = {
                        onUiEvent(MainUiEvent.StartDateChanged(it.toString()))
                        onUiEvent(MainUiEvent.StartDateDialogPressed)
                    },
                    onCancelled = { onUiEvent(MainUiEvent.StartDateDialogPressed) },
                    shouldFinalizeOnSelect = false,
                )
            }
        }

        Text(text = "시작시간: ")
        InputField(
            modifier = Modifier.weight(1f),
            value = startTime,
            onValueChange = {
                onUiEvent(MainUiEvent.StartTimeChanged(it))
            },
            hasError = startTimeInputFieldHasError,
            errorMessage = startTimeInputFieldErrorMessage,
            placeholder = "예시> 0900",
            singleLine = true,
        )
    }
}

@Composable
fun EndDateTimeTextField(
    modifier: Modifier = Modifier,
    endDateDialogState: Boolean,
    endDate: String,
    endTime: String,
    endTimeInputFieldHasError: Boolean,
    endTimeInputFieldErrorMessage: String?,
    onUiEvent: (MainUiEvent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(text = "종료날짜: ")
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onUiEvent(MainUiEvent.EndDateDialogPressed)

                },
            text = if (endDate == "") {
                " -> 선택(클릭)"
            } else {
                endDate
            },
            color = if (endDate == "") {
                Color.Blue
            } else {
                Color.Black
            }
        )

        if (endDateDialogState) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                com.baec23.ludwig.component.datepicker.DatePicker(
                    onDateSelectionFinalized = {
                        onUiEvent(MainUiEvent.EndDateChanged(it.toString()))
                        onUiEvent(MainUiEvent.EndDateDialogPressed)
                    },
                    onCancelled = { onUiEvent(MainUiEvent.EndDateDialogPressed) },
                    shouldFinalizeOnSelect = false,
                )
            }
        }

        Text(text = "종료시간: ")
        InputField(
            modifier = Modifier.weight(1f),
            value = endTime,
            onValueChange = {
                onUiEvent(MainUiEvent.EndTimeChanged(it))
            },
            hasError = endTimeInputFieldHasError,
            errorMessage = endTimeInputFieldErrorMessage,
            placeholder = "예시> 0900",
            singleLine = true,
        )
    }
}


@Composable
fun TimeOffRequestTypeDropDownMenu(
    modifier: Modifier = Modifier,
    title: String,
    items: List<TimeOffRequestType>,
    expanded: Boolean,
    onUiEvent: (MainUiEvent) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxHeight(0.1f)
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        onUiEvent(
                            MainUiEvent.TimeOffRequestTypeExpanded(true)
                        )
                    })
                    .background(Color.LightGray),
                text = items[selectedIndex].toKorean(),
            )
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                expanded = expanded,
                onDismissRequest = {
                    onUiEvent(
                        MainUiEvent.TimeOffRequestTypeExpanded(false)
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
                            onUiEvent(MainUiEvent.TimeOffRequestTypeExpanded(false))
                            onUiEvent(MainUiEvent.TimeOffRequestType(items[selectedIndex]))
                        },
                        colors = MenuDefaults.itemColors(textColor = Color.Black)
                    )
                }
            }
        }
    }
}

@Composable
fun TimeOffRequestTypeDetailsDropDownMenu(
    modifier: Modifier = Modifier,
    title: String,
    items: List<TimeOffRequestTypeDetail>,
    expanded: Boolean,
    onUiEvent: (MainUiEvent) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxHeight(0.1f)
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart),
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        onUiEvent(
                            MainUiEvent.TimeOffRequestTypeDetailsExpanded(true)
                        )
                    })
                    .background(Color.LightGray),
                text = items[selectedIndex].toKorean(),
            )
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                expanded = expanded,
                onDismissRequest = {
                    onUiEvent(
                        MainUiEvent.TimeOffRequestTypeDetailsExpanded(false)
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
                            onUiEvent(MainUiEvent.TimeOffRequestTypeDetailsExpanded(false))
                            onUiEvent(MainUiEvent.TimeOffRequestTypeDetails(items[selectedIndex]))
                        },
                        colors = MenuDefaults.itemColors(textColor = Color.Black),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeOffRequestReasonBox(
    modifier: Modifier = Modifier,
    title: String,
    requestReason: String,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(bottom = 5.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.2f),
            text = title
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(1.dp),
            value = requestReason,
            onValueChange = {
                onUiEvent(MainUiEvent.TimeOffRequestReasonChanged(it))
            },
            maxLines = 4,
            placeholder = { Text(text = "예시> 개인사유") }
        )

    }
}

@Composable
fun AgentNameBox(
    modifier: Modifier = Modifier,
    title: String,
    agentName: String,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(0.25f),
            text = title
        )
        InputField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = agentName,
            onValueChange = { onUiEvent(MainUiEvent.AgentNameChanged(it)) },
            singleLine = true,
            placeholder = "예시> 홍길동",
        )
    }
}

@Composable
fun EmergencyNumberBox(
    modifier: Modifier = Modifier,
    title: String,
    emergencyNumber: String,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.25f),
            text = title
        )
        InputField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = emergencyNumber,
            onValueChange = { onUiEvent(MainUiEvent.EmergencyNumberChanged(it)) },
            singleLine = true,
            placeholder = "예시> 01012345678",
        )
    }
}
