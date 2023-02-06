@file:OptIn(ExperimentalMaterial3Api::class)

package com.gausslab.timeoffrequester.ui.screen.requestform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.ButtonState
import com.baec23.ludwig.component.button.LabelledValueButton
import com.baec23.ludwig.component.button.StatefulButton
import com.baec23.ludwig.component.datepicker.DatePicker
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.section.DisplaySection
import com.baec23.ludwig.component.section.ExpandableDisplaySection
import com.baec23.ludwig.component.timepicker.TimePicker
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
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
    val startDateTimeExpanded by viewModel.startDateTimeExpanded
    val endDateTimeExpanded by viewModel.endDateTimeExpanded

    val dateExpanded by viewModel.dateExpanded
    val timeExpanded by viewModel.timeExpanded

    val startDate by viewModel.selectedStartDate.collectAsState()
    val startTime by viewModel.selectedStartTime.collectAsState()
    val endDate by viewModel.selectedEndDate.collectAsState()
    val endTime by viewModel.selectedEndTime.collectAsState()
    val reason by viewModel.reasonText.collectAsState()

    val valueFontSize = MaterialTheme.typography.titleMedium.fontSize
    val valueFontColor = MaterialTheme.colorScheme.onPrimaryContainer

    val labelFontSize = MaterialTheme.typography.labelMedium.fontSize
    val labelFontColor = Color.DarkGray

    val remainingTimeOffRequests by viewModel.remainingTimeOffRequests.collectAsState()
    val remainingTimeOffRequestValid by viewModel.remainingTimeOffRequestValid.collectAsState()

    val isBusy by viewModel.isBusy.collectAsState()

    val items = listOf(
        TimeOffRequestTypeDetail.FUNERAL_LEAVE,
        TimeOffRequestTypeDetail.MARRIAGE_LEAVE,
        TimeOffRequestTypeDetail.OTHER
    )
    val isTimeOffRequestTypeDetailsExpanded by viewModel.timeOffRequestTypeDetailsExpanded
    val isAdditionalInformationExpanded by viewModel.expandableSessionState

    val agentName by viewModel.agentName.collectAsState()
    val emergencyNumber by viewModel.emergencyNumber.collectAsState()

    AnimatedVisibility(visible = isBusy) {
        AlertDialog(onDismissRequest = { }) {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("작업중...", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DisplaySection(headerText = "연차 신청하기") {
            Text("남은 연차 - $remainingTimeOffRequests", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(5.dp))
            DisplaySection(headerText = "신청 시간") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelledValueButton(
                        onClick = {
                            viewModel.onEvent(RequestFormUiEvent.StartDateTimeCardExpanded(true))
                            viewModel.onEvent(RequestFormUiEvent.IsDateExpanded(true))
                            viewModel.onEvent(RequestFormUiEvent.IsTimeExpanded(true))
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
                        isShowing = startDateTimeExpanded,
                        onCancel = { viewModel.onEvent(RequestFormUiEvent.StartDateTimeCardExpanded(false))},
                        initialDate = startDate,
                        initialTime = startTime,
                        dateExpanded = dateExpanded,
                        timeExpanded = timeExpanded,
                        onUiEvent = {viewModel.onEvent(it)},
                        onDateTimeSelected = { selectedDate, selectedTime ->
                            viewModel.onEvent(
                                RequestFormUiEvent.OnSelectedStartDateChanged(
                                    selectedDate
                                )
                            )
                            viewModel.onEvent(
                                RequestFormUiEvent.OnSelectedStartTimeChanged(
                                    selectedTime
                                )
                            )
                            viewModel.onEvent(RequestFormUiEvent.StartDateTimeCardExpanded(false))
                        }
                    )

                    LabelledValueButton(
                        onClick = {
                            viewModel.onEvent(RequestFormUiEvent.EndDateTimeCardExpanded(true))
                            viewModel.onEvent(RequestFormUiEvent.IsDateExpanded(true))
                            viewModel.onEvent(RequestFormUiEvent.IsTimeExpanded(true))
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
                        isShowing = endDateTimeExpanded,
                        onCancel = { viewModel.onEvent(RequestFormUiEvent.EndDateTimeCardExpanded(false)) },
                        initialDate = endDate,
                        initialTime = endTime,
                        dateExpanded = dateExpanded,
                        timeExpanded = timeExpanded,
                        onUiEvent = {viewModel.onEvent(it)},
                        onDateTimeSelected = { selectedDate, selectedTime ->
                            viewModel.onEvent(
                                RequestFormUiEvent.OnSelectedEndDateChanged(
                                    selectedDate
                                )
                            )
                            viewModel.onEvent(
                                RequestFormUiEvent.OnSelectedEndTimeChanged(
                                    selectedTime
                                )
                            )
                            viewModel.onEvent(RequestFormUiEvent.EndDateTimeCardExpanded(false))
                        }
                    )
                }
            }
            ExpandableDisplaySection(
                isExpanded = isAdditionalInformationExpanded,
                headerText = "세부 추가 정보 입력",
                onExpand = { viewModel.onEvent(RequestFormUiEvent.ExpandableSessionPressed) }
            ) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "신청 사유", color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(10.dp))
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = reason,
                        onValueChange = { viewModel.onEvent(RequestFormUiEvent.OnReasonChanged(it)) },
                        minLines = 1,
                        maxLines = 1,
                        placeholder = "신청 사유를 적어주세요\n예) 개인사유"
                    )
                }
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier =Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "대리업무자", color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(10.dp))
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = agentName,
                        onValueChange = { viewModel.onEvent(RequestFormUiEvent.AgentNameChanged(it)) },
                        minLines = 1,
                        maxLines = 1,
                        placeholder = "휴가 중 대리업무자를 적어주세요\n예) 홍길동"
                    )
                }
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.fillMaxWidth(0.15f), fontSize = 11.sp, text = "비상연락망", color = Color.DarkGray)
                    Spacer(modifier = Modifier.width(10.dp))
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = emergencyNumber,
                        onValueChange = { viewModel.onEvent(RequestFormUiEvent.EmergencyNumberChanged(it)) },
                        minLines = 1,
                        maxLines = 1,
                        placeholder = "휴가 중 비상연락망을 적어주세요\n예) 홍길동"
                    )
                }
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
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
                                        RequestFormUiEvent.TimeOffRequestTypeDetailsExpanded(
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
                                    RequestFormUiEvent.TimeOffRequestTypeDetailsExpanded(
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
                                            RequestFormUiEvent.TimeOffRequestTypeDetailsExpanded(
                                                false
                                            )
                                        )
                                        viewModel.onEvent(
                                            RequestFormUiEvent.TimeOffRequestTypeDetails(
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
            StatefulButton(
                modifier = Modifier.fillMaxWidth(),
                text = "신청",
                state = if (remainingTimeOffRequestValid){
                    ButtonState.Enabled
                }else{
                    ButtonState.Disabled
                }
            ) {
                viewModel.onEvent(RequestFormUiEvent.OnSubmitPressed)
            }
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
    dateExpanded : Boolean,
    timeExpanded : Boolean,
    onDateTimeSelected: (LocalDate, LocalTime) -> Unit,
    onUiEvent : (RequestFormUiEvent)->Unit
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    var selectedTime by remember { mutableStateOf(initialTime) }

    AnimatedVisibility(visible = isShowing) {
        AlertDialog(onDismissRequest = { }) {
            AnimatedVisibility(
                visible = dateExpanded, //t
                enter = scaleIn(),
                exit = fadeOut()
            ) {
                DatePicker(
                    onCancelled = onCancel,
                    onDateSelectionFinalized = {
                        selectedDate = it
                        onUiEvent(RequestFormUiEvent.IsDateExpanded(false))
                    },
                    shouldFinalizeOnSelect = true,
                    initialDate = initialDate
                )
            }
            AnimatedVisibility(
                visible = !dateExpanded && timeExpanded, //f t
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
                                    onUiEvent(RequestFormUiEvent.IsTimeExpanded(false))
                                }
                                StatefulButton(text = "취소") {
                                    onUiEvent(RequestFormUiEvent.IsDateExpanded(true))
                                    onUiEvent(RequestFormUiEvent.IsTimeExpanded(true))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
