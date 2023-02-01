@file:OptIn(ExperimentalMaterial3Api::class)

package com.gausslab.timeoffrequester.ui.screen.requestform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.LabelledValueButton
import com.baec23.ludwig.component.button.StatefulButton
import com.baec23.ludwig.component.datepicker.DatePicker
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.component.timepicker.TimePicker
import java.time.LocalDate
import java.time.LocalTime

const val requestFormScreenRoute = "request_form_screen_route"
fun NavGraphBuilder.requestFormScreen() {
    composable(route = requestFormScreenRoute) {
        RequestFormScreen()
    }
}

fun NavController.navigateToRequestFormScreen(navOptions: NavOptions? = null) {
    navigate(route = requestFormScreenRoute, navOptions = navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestFormScreen(
    viewModel: RequestFormViewModel = hiltViewModel()
) {
    val startDate by viewModel.selectedStartDate.collectAsState()
    val startTime by viewModel.selectedStartTime.collectAsState()
    val endDate by viewModel.selectedEndDate.collectAsState()
    val endTime by viewModel.selectedEndTime.collectAsState()
    val reason by viewModel.reasonText.collectAsState()

    var isStartDialogShowing by remember { mutableStateOf(false) }
    var isEndDialogShowing by remember { mutableStateOf(false) }

    val valueFontSize = MaterialTheme.typography.titleMedium.fontSize
    val valueFontColor = MaterialTheme.colorScheme.onPrimaryContainer

    val labelFontSize = MaterialTheme.typography.labelMedium.fontSize
    val labelFontColor = Color.DarkGray

    val remainingTimeOffRequests by viewModel.remainingTimeOffRequests.collectAsState()

    val isBusy by viewModel.isBusy.collectAsState()

    AnimatedVisibility(visible = isBusy) {
        AlertDialog(onDismissRequest = { }) {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("작업중...", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }


    DisplaySection(headerText = "연차 신청하기" ) {
        Text("남은 연차 - $remainingTimeOffRequests", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(5.dp))
        DisplaySection(headerText = "신청 시간") {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                LabelledValueButton(
                    onClick = {
                        isStartDialogShowing = true
                    }, label = { Text("시작시간") },
                    value = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = startDate.toAnnotatedString(
                                    valueFontSize,
                                    valueFontColor,
                                    labelFontSize,
                                    labelFontColor
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = startTime.toAnnotatedString(
                                    valueFontSize,
                                    valueFontColor,
                                    labelFontSize,
                                    labelFontColor
                                )
                            )
                        }
                    })
                DateTimePickerDialog(
                    isShowing = isStartDialogShowing,
                    onCancel = { isStartDialogShowing = false },
                    initialDate = startDate,
                    initialTime = startTime,
                    onDateTimeSelected = { selectedDate, selectedTime ->
                        viewModel.onEvent(RequestFormUiEvent.OnSelectedStartDateChanged(selectedDate))
                        viewModel.onEvent(RequestFormUiEvent.OnSelectedStartTimeChanged(selectedTime))
                        isStartDialogShowing = false
                    }
                )

                LabelledValueButton(
                    onClick = {
                        isEndDialogShowing = true
                    }, label = { Text("종료시간") },
                    value = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = endDate.toAnnotatedString(
                                    valueFontSize,
                                    valueFontColor,
                                    labelFontSize,
                                    labelFontColor
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = endTime.toAnnotatedString(
                                    valueFontSize,
                                    valueFontColor,
                                    labelFontSize,
                                    labelFontColor
                                )
                            )
                        }
                    })
                DateTimePickerDialog(
                    isShowing = isEndDialogShowing,
                    onCancel = { isEndDialogShowing = false },
                    initialDate = endDate,
                    initialTime = endTime,
                    onDateTimeSelected = { selectedDate, selectedTime ->
                        viewModel.onEvent(RequestFormUiEvent.OnSelectedEndDateChanged(selectedDate))
                        viewModel.onEvent(RequestFormUiEvent.OnSelectedEndTimeChanged(selectedTime))
                        isEndDialogShowing = false
                    }
                )
            }
        }
        DisplaySection(headerText = "상세 내용") {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                value = reason,
                onValueChange = { viewModel.onEvent(RequestFormUiEvent.OnReasonChanged(it)) },
                minLines = 1,
                maxLines = 1,
                label = "신청 사유",
                placeholder = "신청 사유를 적어주세요\n예) 개인사유"
            )
        }
        StatefulButton(modifier = Modifier.fillMaxWidth(), text = "신청") {
            viewModel.onEvent(RequestFormUiEvent.OnSubmitPressed)
        }
    }
}

private fun LocalTime.toAnnotatedString(
    valueFontSize: TextUnit,
    valueColor: Color,
    labelFontSize: TextUnit,
    labelColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        val valueStyle = SpanStyle(fontSize = valueFontSize, color = valueColor)
        val labelStyle = SpanStyle(fontSize = labelFontSize, color = labelColor)
        pushStyle(valueStyle)
        append(hour.toString())
        pop()
        pushStyle(labelStyle)
        append("시")
        pop()
        append(" ")

        pushStyle(valueStyle)
        append(minute.toString())
        pop()
        pushStyle(labelStyle)
        append("분")
        pop()
        append(" ")

        toAnnotatedString()
    }
}

private fun LocalDate.toAnnotatedString(
    valueFontSize: TextUnit,
    valueColor: Color,
    labelFontSize: TextUnit,
    labelColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        val valueStyle = SpanStyle(fontSize = valueFontSize, color = valueColor)
        val labelStyle = SpanStyle(fontSize = labelFontSize, color = labelColor)
        pushStyle(valueStyle)
        append(year.toString())
        pop()
        pushStyle(labelStyle)
        append("년")
        pop()
        append(" ")

        pushStyle(valueStyle)
        append(monthValue.toString())
        pop()
        pushStyle(labelStyle)
        append("월")
        pop()
        append(" ")

        pushStyle(valueStyle)
        append(dayOfMonth.toString())
        pop()
        pushStyle(labelStyle)
        append("일")
        pop()
        append(" ")

        toAnnotatedString()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DateTimePickerDialog(
    isShowing: Boolean,
    onCancel: () -> Unit,
    initialDate: LocalDate,
    initialTime: LocalTime,
    onDateTimeSelected: (LocalDate, LocalTime) -> Unit
) {
    var isDateSelected by remember { mutableStateOf(false) }
    var isTimeSelected by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf(initialDate) }
    var selectedTime by remember { mutableStateOf(initialTime) }

    AnimatedVisibility(visible = isShowing) {
        AlertDialog(onDismissRequest = { onCancel() }) {
            AnimatedVisibility(
                visible = !isDateSelected,
                enter = scaleIn(),
                exit = fadeOut()
            ) {
                DatePicker(
                    onCancelled = onCancel,
                    onDateSelectionFinalized = {
                        selectedDate = it
                        isDateSelected = true
                    },
                    shouldFinalizeOnSelect = true
                )
            }
            AnimatedVisibility(
                visible = isDateSelected && !isTimeSelected,
                enter = scaleIn(),
                exit = fadeOut()
            ) {
                Card(shape = RoundedCornerShape(6.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        DisplaySection(
                            headerText = "${selectedDate.year}년 ${selectedDate.monthValue}월 ${selectedDate.dayOfMonth}일",
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TimePicker(
                                initialTime = selectedTime,
                                onTimeChanged = { selectedTime = it })
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                StatefulButton(text = "완료") {
                                    onDateTimeSelected(selectedDate, selectedTime)
                                    isTimeSelected = true
                                }
                                StatefulButton(text = "취소") {
                                    isDateSelected = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}