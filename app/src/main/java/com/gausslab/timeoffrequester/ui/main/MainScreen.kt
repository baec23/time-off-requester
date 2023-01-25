package com.gausslab.timeoffrequester.ui.main

import android.provider.ContactsContract.CommonDataKinds.Phone
import android.telephony.PhoneNumberUtils
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.datepicker.DatePicker
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.inputfield.InputValidator
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.component.section.ExpandableDisplaySection
import com.baec23.ludwig.component.timepicker.TimePicker
import com.baec23.ludwig.component.toggleable.ToggleableIconColumn
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
    val startTimeDialogState by viewModel.startTimeDialogState
    val startTime = formState.startTime

    val endDateDialogState by viewModel.endDateDialogState
    val endDate = formState.endDate
    val endTimeDialogState by viewModel.endTimeDialogState
    val endTime = formState.endTime

    val isAdditionalInformationExpanded by viewModel.expandableSessionState
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
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "< 남은 연차 수 : $remainingTimeOffRequest >",
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(15.dp))
            TimeOffRequestForm(
                startDateDialogState = startDateDialogState,
                startDate = startDate,
                startTimeDialogState = startTimeDialogState,
                startTime = startTime,
                endDateDialogState = endDateDialogState,
                endDate = endDate,
                endTimeDialogState = endTimeDialogState,
                endTime = endTime,
                isAdditionalInformationExpanded = isAdditionalInformationExpanded,
                isTimeOffRequestTypeExpanded = isTimeOffRequestTypeExpanded,
                isTimeOffRequestTypeDetailsExpanded = isTimeOffRequestTypeDetailsExpanded,
                requestReason = requestReason,
                agentName = agentName,
                emergencyNumber = emergencyNumber,
                onUiEvent = { viewModel.onEvent(it) }
            )
            Spacer(modifier = Modifier.height(15.dp))
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
    startTimeDialogState: Boolean,
    startDate: LocalDate,
    startTime: LocalTime,
    endDateDialogState: Boolean,
    endTimeDialogState: Boolean,
    endDate: LocalDate,
    endTime: LocalTime,
    isAdditionalInformationExpanded: Boolean,
    isTimeOffRequestTypeExpanded: Boolean,
    isTimeOffRequestTypeDetailsExpanded: Boolean,
    requestReason: String,
    agentName: String,
    emergencyNumber: String,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Surface(modifier = modifier) {
        Column() {
            DateTimeSection(
                startDateDialogState = startDateDialogState,
                startDate = startDate,
                startTimeDialogState = startTimeDialogState,
                startTime = startTime,
                endDateDialogState = endDateDialogState,
                endDate = endDate,
                endTimeDialogState = endTimeDialogState,
                endTime = endTime,
                onUiEvent = onUiEvent
            )
            TimeOffRequestReasonBox(
                requestReason = requestReason,
                onUiEvent = onUiEvent
            )
            TimeOffRequestTypeDropDownMenu(
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
            ExpandableDisplaySection(
                isExpanded = isAdditionalInformationExpanded,
                headerText = "추가 세부 입력",
                onExpand = { onUiEvent(MainUiEvent.ExpandableSessionPressed) }
            ) {
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
}

@Composable
fun DateTimeSection(
    modifier: Modifier = Modifier,
    startDateDialogState: Boolean,
    startTimeDialogState: Boolean,
    startDate: LocalDate,
    startTime: LocalTime,
    endDateDialogState: Boolean,
    endTimeDialogState: Boolean,
    endDate: LocalDate,
    endTime: LocalTime,
    onUiEvent: (MainUiEvent) -> Unit
) {
    DisplaySection(
        modifier = modifier
            .height(135.dp),
        headerText = "* 날짜/시간"
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .clickable {
                        onUiEvent(MainUiEvent.StartDateDialogPressed)
                    },
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar Icon",
            )
            Text(
                modifier = Modifier.clickable { onUiEvent(MainUiEvent.StartDateDialogPressed) },
                text = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
            if (startDateDialogState) {
                Dialog(onDismissRequest = { }) {
                    DatePicker(
                        onDateSelectionFinalized = {
                            onUiEvent(MainUiEvent.StartDateChanged(it))
                            onUiEvent(MainUiEvent.StartDateDialogPressed)
                        },
                        onCancelled = { onUiEvent(MainUiEvent.StartDateDialogPressed) },
                        shouldFinalizeOnSelect = false,
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                modifier = Modifier.clickable { onUiEvent(MainUiEvent.StartTimeDialogPressed) },
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Icon"
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onUiEvent(MainUiEvent.StartTimeDialogPressed)
                    },
                text = startTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )
            if (startTimeDialogState) {
                Dialog(onDismissRequest = { }) {
                    var currTime: LocalTime = LocalTime.now()
                    Column() {
                        TimePicker(
                            modifier = Modifier.weight(1f),
                            onTimeChanged = {
                                currTime = it
                            },
                            initialTime = startTime
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp), horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(modifier = Modifier.background(color = Color.White),
                                onClick = { onUiEvent(MainUiEvent.StartTimeDialogPressed) }
                            ) {
                                Text("Cancel", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            TextButton(
                                modifier = Modifier.background(color = Color.White),
                                onClick = {
                                    onUiEvent(MainUiEvent.StartTimeChanged(currTime))
                                    onUiEvent(MainUiEvent.StartTimeDialogPressed)
                                }
                            ) {
                                Text("OK", color = Color.Black)
                            }
                        }
                    }

                }
            }
        }
        Image(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = Icons.Default.SyncAlt,
            contentDescription = null
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier
                    .clickable {
                        onUiEvent(MainUiEvent.EndDateDialogPressed)
                    },
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Calendar Icon",
            )
            Text(
                modifier = Modifier.clickable { onUiEvent(MainUiEvent.EndDateDialogPressed) },
                text = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
            if (endDateDialogState) {
                Dialog(onDismissRequest = { }) {
                    DatePicker(
                        onDateSelectionFinalized = {
                            onUiEvent(MainUiEvent.EndDateChanged(it))
                            onUiEvent(MainUiEvent.EndDateDialogPressed)
                        },
                        onCancelled = { onUiEvent(MainUiEvent.EndDateDialogPressed) },
                        shouldFinalizeOnSelect = false,
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                modifier = Modifier.clickable { onUiEvent(MainUiEvent.EndTimeDialogPressed) },
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Icon"
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onUiEvent(MainUiEvent.EndTimeDialogPressed)
                    },
                text = endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            )
            if (endTimeDialogState) {
                Dialog(onDismissRequest = { }) {
                    var currTime: LocalTime = LocalTime.now()
                    Column() {
                        TimePicker(
                            modifier = Modifier.weight(1f),
                            onTimeChanged = {
                                currTime = it
                            },
                            initialTime = endTime
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp), horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(modifier = Modifier.background(color = Color.White),
                                onClick = { onUiEvent(MainUiEvent.EndTimeDialogPressed) }
                            ) {
                                Text("Cancel", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            TextButton(
                                modifier = Modifier.background(color = Color.White),
                                onClick = {
                                    onUiEvent(MainUiEvent.EndTimeChanged(currTime))
                                    onUiEvent(MainUiEvent.EndTimeDialogPressed)
                                }
                            ) {
                                Text("OK", color = Color.Black)
                            }
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeOffRequestReasonBox(
    modifier: Modifier = Modifier,
    requestReason: String,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    DisplaySection(
        modifier = modifier, headerText = "* 신청사유"
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(10.dp),
            value = requestReason,
            onValueChange = {
                onUiEvent(MainUiEvent.TimeOffRequestReasonChanged(it))
            },
            maxLines = 3,
            placeholder = { Text(text = "예시> 개인사유") }
        )
    }
}

@Composable
fun TimeOffRequestTypeDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<TimeOffRequestType>,
    expanded: Boolean,
    onUiEvent: (MainUiEvent) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    DisplaySection(
        modifier = modifier.height(80.dp),
        headerText = "* 휴가구분"
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
                .border(1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
                    .clickable(onClick = {
                        onUiEvent(
                            MainUiEvent.TimeOffRequestTypeExpanded(true)
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
    var selectedIndex by remember { mutableStateOf(2) }

    Row(
        modifier = modifier
            .fillMaxHeight(0.2f)
    ) {
        Text(text = title)
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
                .border(1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
                    .clickable(onClick = {
                        onUiEvent(
                            MainUiEvent.TimeOffRequestTypeDetailsExpanded(true)
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
                .height(30.dp),
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
                .height(30.dp),
            value = emergencyNumber,
            onValueChange = { onUiEvent(MainUiEvent.EmergencyNumberChanged(it)) },
            singleLine = true,
            placeholder = "예시> 01012345678",
            inputValidator = InputValidator.Number,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
