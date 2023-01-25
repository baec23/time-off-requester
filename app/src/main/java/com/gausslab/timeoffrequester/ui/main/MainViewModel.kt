package com.gausslab.timeoffrequester.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.ui.requestlist.navigateToRequestListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val timeOffRequestRepository: TimeOffRequestRepository,
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    val startDateDialogState: MutableState<Boolean> = mutableStateOf(false)
    val endDateDialogState: MutableState<Boolean> = mutableStateOf(false)

    val startTimeDialogState: MutableState<Boolean> = mutableStateOf(false)
    val endTimeDialogState: MutableState<Boolean> = mutableStateOf(false)

    val timeOffRequestType: MutableState<Boolean> = mutableStateOf(false)
    val timeOffRequestTypeDetails: MutableState<Boolean> = mutableStateOf(false)

    private val _formState: MutableState<TimeOffRequestFormState> = mutableStateOf(
        TimeOffRequestFormState()
    )
    val formState: State<TimeOffRequestFormState> = _formState

    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid

    val remainingTimeOffRequest: Int = userRepository.currUser!!.remainingTimeOffRequests
    private fun checkDate() {
        val form by _formState
        var isValid = false
        if (form.startDate < form.endDate) {
            isValid = true
        } else if (form.startDate == form.endDate) {
            if (form.startTime < form.endTime) {
                isValid = true
            }
        } else {
            isValid = false
        }
        _isFormValid.value = isValid
    }

    fun onEvent(event: MainUiEvent) {
        when (event) {

            is MainUiEvent.StartDateChanged -> {
                _formState.value = _formState.value.copy(
                    startDate = event.startDate
                )
            }

            is MainUiEvent.StartTimeChanged -> {
                _formState.value = _formState.value.copy(
                    startTime = event.startTime
                )
            }

            is MainUiEvent.EndDateChanged -> {
                _formState.value = _formState.value.copy(
                    endDate = event.endDate
                )
            }

            is MainUiEvent.EndTimeChanged -> {
                _formState.value = _formState.value.copy(
                    endTime = event.endTime
                )
            }

            is MainUiEvent.TimeOffRequestTypeExpanded -> {
                timeOffRequestType.value = event.expanded
            }

            is MainUiEvent.TimeOffRequestType -> {
                _formState.value = _formState.value.copy(
                    timeOffRequestType = event.type
                )
            }

            is MainUiEvent.TimeOffRequestTypeDetailsExpanded -> {
                timeOffRequestTypeDetails.value = event.expanded
            }

            is MainUiEvent.TimeOffRequestTypeDetails -> {
                _formState.value = _formState.value.copy(
                    timeOffRequestTypeDetails = event.type
                )
            }

            is MainUiEvent.TimeOffRequestReasonChanged -> {
                _formState.value = _formState.value.copy(
                    requestReason = event.reason
                )
            }

            is MainUiEvent.AgentNameChanged -> {
                _formState.value = _formState.value.copy(
                    agentName = event.agentName
                )
            }

            is MainUiEvent.EmergencyNumberChanged -> {
                _formState.value = _formState.value.copy(
                    emergencyNumber = event.emergencyNumber
                )
            }

            MainUiEvent.SubmitButtonPressed -> {
                viewModelScope.launch {
                    val form by _formState
                    val timeOffRequest = TimeOffRequest(
                        status = "",
                        userId = userRepository.currUser!!.id,
                        username = userRepository.currUser!!.username,
                        position = userRepository.currUser!!.position,
                        userStartDate = userRepository.currUser!!.startDate,
                        timeOffRequestId = 0,
                        startDate = form.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        startTime = form.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        endDate = form.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        endTime = form.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        timeOffRequestType = form.timeOffRequestType,
                        timeOffRequestTypeDetails = form.timeOffRequestTypeDetails,
                        requestReason = form.requestReason,
                        agentName = form.agentName,
                        emergencyNumber = form.emergencyNumber
                    )
                    timeOffRequestRepository.saveNewTimeOffRequest(timeOffRequest)
                    userRepository.reduceRemainingTimeOffRequests(userRepository.currUser!!.id)
                    navController.navigateToRequestListScreen()

                }
            }

            MainUiEvent.StartDateDialogPressed -> {
                startDateDialogState.value = !startDateDialogState.value
            }

            MainUiEvent.EndDateDialogPressed -> {
                endDateDialogState.value = !endDateDialogState.value
            }

            MainUiEvent.StartTimeDialogPressed -> {
                startTimeDialogState.value = !startTimeDialogState.value
            }

            MainUiEvent.EndTimeDialogPressed -> {
                endTimeDialogState.value = !endTimeDialogState.value
            }
        }
        checkDate()
    }
}

data class TimeOffRequestFormState(
    val startDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endDate: LocalDate = LocalDate.now(),
    val endTime: LocalTime = LocalTime.now(),
    val timeOffRequestType: TimeOffRequestType = TimeOffRequestType.ANNUAL_LEAVE,
    val timeOffRequestTypeDetails: TimeOffRequestTypeDetail = TimeOffRequestTypeDetail.FUNERAL_LEAVE,
    val requestReason: String = "",
    val agentName: String = "",
    val emergencyNumber: String = ""
)

sealed class MainUiEvent {
    data class StartDateChanged(val startDate: LocalDate) : MainUiEvent()
    data class StartTimeChanged(val startTime: LocalTime) : MainUiEvent()
    data class EndDateChanged(val endDate: LocalDate) : MainUiEvent()
    data class EndTimeChanged(val endTime: LocalTime) : MainUiEvent()
    data class TimeOffRequestTypeExpanded(val expanded: Boolean) : MainUiEvent()
    data class TimeOffRequestType(val type: com.gausslab.timeoffrequester.model.TimeOffRequestType) :
        MainUiEvent()

    data class TimeOffRequestTypeDetailsExpanded(val expanded: Boolean) : MainUiEvent()
    data class TimeOffRequestTypeDetails(val type: TimeOffRequestTypeDetail) : MainUiEvent()
    data class TimeOffRequestReasonChanged(val reason: String) : MainUiEvent()
    data class AgentNameChanged(val agentName: String) : MainUiEvent()
    data class EmergencyNumberChanged(val emergencyNumber: String) : MainUiEvent()
    object StartDateDialogPressed : MainUiEvent()
    object EndDateDialogPressed : MainUiEvent()
    object StartTimeDialogPressed : MainUiEvent()
    object EndTimeDialogPressed : MainUiEvent()
    object SubmitButtonPressed : MainUiEvent()
}