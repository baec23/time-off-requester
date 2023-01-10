package com.gausslab.timeoffrequester.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepository
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val timeOffRequestRepository: TimeOffRequestRepository,
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {
    val startDateInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val startDateInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    val startTimeInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val startTimeInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    val endDateInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val endDateInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    val endTimeInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val endTimeInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    val timeOffRequestType: MutableState<Boolean> = mutableStateOf(false)
    val timeOffRequestTypeDetails: MutableState<Boolean> = mutableStateOf(false)

    private val _formState: MutableState<TimeOffRequestFormState> = mutableStateOf(
        TimeOffRequestFormState()
    )
    val formState: State<TimeOffRequestFormState> = _formState

    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid

    private fun updateIsFormValid() {
        val form by _formState
        var isValid = true
        if (form.startDate.length != 6 || form.startTime.length != 4) {
            isValid = false
        } else if (form.endDate.length != 6 || form.endTime.length != 4) {
            isValid = false
        } else if (form.requestReason.isEmpty()) {
            isValid = false
        }
        _isFormValid.value = isValid
    }

    private fun String.isValidDate(): Boolean {
        return this.length == 6
    }

    private fun String.isValidTime(): Boolean {
        return this.length == 4
    }

    fun onEvent(event: MainUiEvent) {
        when (event) {

            is MainUiEvent.StartDateChanged -> {
                _formState.value = _formState.value.copy(
                    startDate = event.startDate
                )
                if (event.startDate.isValidDate()) {
                    startDateInputFieldHasError.value = false
                } else {
                    startDateInputFieldHasError.value = true
                    startDateInputFieldErrorMessage.value = "시작 날짜 이상함"
                }
            }

            is MainUiEvent.StartTimeChanged -> {
                _formState.value = _formState.value.copy(
                    startTime = event.startTime
                )
                if (event.startTime.isValidTime()) {
                    startTimeInputFieldHasError.value = false
                } else {
                    startTimeInputFieldHasError.value = true
                    startTimeInputFieldErrorMessage.value = "시작 시간 이상함"
                }
            }

            is MainUiEvent.EndDateChanged -> {
                _formState.value = _formState.value.copy(
                    endDate = event.endDate
                )
                if (event.endDate.isValidDate()) {
                    endDateInputFieldHasError.value = false
                } else {
                    endDateInputFieldHasError.value = true
                    endDateInputFieldErrorMessage.value = "종료 날짜 이상함"
                }
            }

            is MainUiEvent.EndTimeChanged -> {
                _formState.value = _formState.value.copy(
                    endTime = event.endTime
                )
                if (event.endTime.isValidTime()) {
                    endTimeInputFieldHasError.value = false
                } else {
                    endTimeInputFieldHasError.value = true
                    endTimeInputFieldErrorMessage.value = "종료 시간 이상함"
                }
            }

            is MainUiEvent.TimeOffRequestTypeExpanded -> {
                timeOffRequestType.value = event.expanded
            }

            is MainUiEvent.TimeOffRequestTypeDetailsExpanded -> {
                timeOffRequestTypeDetails.value = event.expanded
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
                        username =userRepository.currUser!!.username,
                        position =userRepository.currUser!!.position,
                        userStartDate =userRepository.currUser!!.startDate,
                        startDate = form.startDate,
                        startTime = form.startTime,
                        endDate = form.endDate,
                        endTime = form.endTime,
                        timeOffRequestType = form.timeOffRequestType,
                        timeOffRequestTypeDetails = form.timeOffRequestTypeDetails,
                        requestReason = form.requestReason,
                        agentName = form.agentName,
                        emergencyNumber = form.emergencyNumber

                    )
                    val result = timeOffRequestRepository.saveTimeOffRequest(timeOffRequest).getOrElse {

                    }
                    Log.d("asdfasdfasdfasd", "onEvent: $result")
                }
            }

            is MainUiEvent.TimeOffRequestType -> TODO()
            is MainUiEvent.TimeOffRequestTypeDetails -> TODO()
        }
        updateIsFormValid()
    }
}

data class TimeOffRequestFormState(
    val startDate: String = "",
    val startTime: String = "",
    val endDate: String = "",
    val endTime: String = "",
    val timeOffRequestType: TimeOffRequestType = TimeOffRequestType.ANNUAL_LEAVE,
    val timeOffRequestTypeDetails: TimeOffRequestTypeDetail = TimeOffRequestTypeDetail.OTHER,
    val requestReason: String = "",
    val agentName: String = "",
    val emergencyNumber: String = ""
)

sealed class MainUiEvent {
    data class StartDateChanged(val startDate: String) : MainUiEvent()
    data class StartTimeChanged(val startTime: String) : MainUiEvent()
    data class EndDateChanged(val endDate: String) : MainUiEvent()
    data class EndTimeChanged(val endTime: String) : MainUiEvent()
    data class TimeOffRequestTypeExpanded(val expanded: Boolean) : MainUiEvent()
    data class TimeOffRequestType(val type: com.gausslab.timeoffrequester.model.TimeOffRequestType) : MainUiEvent()
    data class TimeOffRequestTypeDetailsExpanded(val expanded: Boolean) : MainUiEvent()
    data class TimeOffRequestTypeDetails(val type: TimeOffRequestTypeDetail): MainUiEvent()
    data class TimeOffRequestReasonChanged(val reason: String) : MainUiEvent()
    data class AgentNameChanged(val agentName: String) : MainUiEvent()
    data class EmergencyNumberChanged(val emergencyNumber: String) : MainUiEvent()
    object SubmitButtonPressed : MainUiEvent()
}