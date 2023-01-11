package com.gausslab.timeoffrequester.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail

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

    val startDate = formState.startDate
    val startDateInputFieldHasError by viewModel.startDateInputFieldHasError
    val startDateInputFieldErrorMessage by viewModel.startDateInputFieldErrorMessage

    val startTime = formState.startTime
    val startTimeInputFieldHasError by viewModel.startTimeInputFieldHasError
    val startTimeInputFieldErrorMessage by viewModel.startTimeInputFieldErrorMessage

    val endDate = formState.endDate
    val endDateInputFieldHasError by viewModel.endDateInputFieldHasError
    val endDateInputFieldErrorMessage by viewModel.endDateInputFieldErrorMessage

    val endTime = formState.endTime
    val endTimeInputFieldHasError by viewModel.endTimeInputFieldHasError
    val endTimeInputFieldErrorMessage by viewModel.endTimeInputFieldErrorMessage

    val isTimeOffRequestTypeExpanded by viewModel.timeOffRequestType
    val isTimeOffRequestTypeDetailsExpanded by viewModel.timeOffRequestTypeDetails

    val requestReason = formState.requestReason
    val agentName =formState.agentName
    val emergencyNumber =formState.emergencyNumber
    val remainingTimeOffRequest = viewModel.remainingTimeOffRequest

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            RemainingTimeOffRequestsBox(remainingTimeOffRequest = remainingTimeOffRequest)
            Spacer(modifier = Modifier.height(30.dp))
            TimeOffRequestForm(
                startDate = startDate,
                startDateInputFieldHasError = startDateInputFieldHasError,
                startDateInputFieldErrorMessage = startDateInputFieldErrorMessage,
                startTime = startTime,
                startTimeInputFieldHasError = startTimeInputFieldHasError,
                startTimeInputFieldErrorMessage = startTimeInputFieldErrorMessage,
                endDate = endDate,
                endDateInputFieldHasError = endDateInputFieldHasError,
                endDateInputFieldErrorMessage = endDateInputFieldErrorMessage,
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
        }
    }
}

@Composable
fun RemainingTimeOffRequestsBox(
    modifier: Modifier = Modifier,
    remainingTimeOffRequest: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.DarkGray, shape = RectangleShape),
    ) {
        Text(modifier = Modifier.padding(5.dp), text = "남은 연차 수 : $remainingTimeOffRequest")
    }
}

@Composable
fun TimeOffRequestForm(
    modifier: Modifier = Modifier,
    startDate: String,
    startDateInputFieldHasError: Boolean,
    startDateInputFieldErrorMessage: String?,
    startTime: String,
    startTimeInputFieldHasError: Boolean,
    startTimeInputFieldErrorMessage: String?,
    endDate: String,
    endDateInputFieldHasError: Boolean,
    endDateInputFieldErrorMessage: String?,
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
                startDate = startDate,
                startDateInputFieldHasError = startDateInputFieldHasError,
                startDateInputFieldErrorMessage = startDateInputFieldErrorMessage,
                startTime = startTime,
                startTimeInputFieldHasError = startTimeInputFieldHasError,
                startTimeInputFieldErrorMessage = startTimeInputFieldErrorMessage,
                onUiEvent = onUiEvent
            )
            EndDateTimeTextField(
                endDate = endDate,
                endDateInputFieldHasError = endDateInputFieldHasError,
                endDateInputFieldErrorMessage = endDateInputFieldErrorMessage,
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
//                items = listOf("연차휴가", "반차", "병가", "출산전후휴가", "경조휴가", "생리휴가", "배우자출산휴가", "공가"),
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
//                items = listOf("결혼", "조의", "기타"),
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
            SubmitButton(
                modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally,
                    ),
                isFormValid,
                onUiEvent = onUiEvent
            )
        }
    }
}

@Composable
fun StartDateTimeTextField(
    modifier: Modifier = Modifier,
    startDate: String,
    startDateInputFieldHasError: Boolean,
    startDateInputFieldErrorMessage: String?,
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
        InputField(
            modifier = Modifier.weight(1f),
            value = startDate,
            onValueChange = {
                onUiEvent(MainUiEvent.StartDateChanged(it))
            },
            hasError = startDateInputFieldHasError,
            errorMessage = startDateInputFieldErrorMessage,
            placeholder = "예시> 220101",
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
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
    endDate: String,
    endDateInputFieldHasError: Boolean,
    endDateInputFieldErrorMessage: String?,
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
        Spacer(modifier = Modifier.width(3.dp))
        InputField(
            modifier = Modifier.weight(1f),
            value = endDate,
            onValueChange = {
                onUiEvent(MainUiEvent.EndDateChanged(it))
            },
            hasError = endDateInputFieldHasError,
            errorMessage = endDateInputFieldErrorMessage,
            placeholder = "예시> 221231",
            singleLine = true,
        )
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
                text = items[selectedIndex].name,
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
                               Text(text = s.name)
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
                text = items[selectedIndex].name,
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
                               Text(text = s.name)
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
            shape = RoundedCornerShape(5.dp),
            value = requestReason,
            onValueChange = { onUiEvent(MainUiEvent.TimeOffRequestReasonChanged(it)) },
            maxLines = 3,
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

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    isFormValid: Boolean,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Button(
            onClick = { onUiEvent(MainUiEvent.SubmitButtonPressed) },
            enabled = isFormValid
        ) {
            Text(text = "SUBMIT!")
        }
    }
}