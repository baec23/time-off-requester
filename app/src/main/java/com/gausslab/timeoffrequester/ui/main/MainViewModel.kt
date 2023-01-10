package com.gausslab.timeoffrequester.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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

    val classificationOfTimeOff: MutableState<Boolean> = mutableStateOf(false)
    val classificationOfTimeOffDetails: MutableState<Boolean> = mutableStateOf(false)

    private val _formState: MutableState<TimeOffRequestFormState> = mutableStateOf(
        TimeOffRequestFormState()
    )
    val formState: State<TimeOffRequestFormState> = _formState

    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid

    private fun checkIfFormIsValid(){
        val form by _formState
        var isValid = true
        if(form.startDate.length!=6 || form.startTime.length!=4){
            isValid = false
        }else if(form.endDate.length!=6 || form.endTime.length!=4){
            isValid = false
        }else if(form.requestReason.isEmpty()){
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
//                _startDateState.value = event.startDate
                checkIfFormIsValid()
            }


            is MainUiEvent.StartDateInputFieldHasError -> {
                startDateInputFieldHasError.value = !startDateInputFieldHasError.value
            }

            is MainUiEvent.StartTimeChanged -> {
                _formState.value = _formState.value.copy(
                    startTime = event.startTime
                )
//                _startTimeState.value = event.startTime
                checkIfFormIsValid()
            }

            is MainUiEvent.StartTimeInputFieldHasError -> {
                startTimeInputFieldHasError.value = !startTimeInputFieldHasError.value
            }

            is MainUiEvent.EndDateChanged -> {
                _formState.value = _formState.value.copy(
                    endDate = event.endDate
                )
//                _endDateState.value = event.endDate
                checkIfFormIsValid()
            }

            is MainUiEvent.EndDateInputFieldHasError -> {
                endDateInputFieldHasError.value = !endDateInputFieldHasError.value
            }

            is MainUiEvent.EndTimeChanged -> {
                _formState.value = _formState.value.copy(
                    endTime = event.endTime
                )
//                _endTimeState.value = event.endTime
                checkIfFormIsValid()
            }

            is MainUiEvent.EndTimeInputFieldHasError -> {
                endTimeInputFieldHasError.value = !endTimeInputFieldHasError.value
            }

            is MainUiEvent.ClassificationOfTimeOffExpanded -> {
                classificationOfTimeOff.value = event.expanded
            }

            is MainUiEvent.ClassificationOfTimeOffDetailsExpanded -> {
                classificationOfTimeOffDetails.value = event.expanded
            }

            is MainUiEvent.TimeOffRequestReasonChanged -> {
                _formState.value = _formState.value.copy(
                    requestReason = event.reason
                )
//                _reasonState.value = event.reason
                checkIfFormIsValid()
            }

            is MainUiEvent.AgentNameChanged -> {
                _formState.value = _formState.value.copy(
                    agentName = event.agentName
                )
//                _agentNameState.value = event.agentName
                checkIfFormIsValid()
            }

            is MainUiEvent.EmergencyNumberChanged -> {
                _formState.value = _formState.value.copy(
                    emergencyNumber = event.emergencyNumber
                )
//                _emergencyNumberState.value = event.emergencyNumber
                checkIfFormIsValid()
            }

            MainUiEvent.StartDateInputFieldErrorMessage -> {
                startDateInputFieldErrorMessage.value = "StartDate format is wrong"
            }

            MainUiEvent.StartTimeInputFieldErrorMessage -> {
                startTimeInputFieldErrorMessage.value = "StartTime format is wrong"
            }

            MainUiEvent.EndDateInputFieldErrorMessage -> {
                endDateInputFieldErrorMessage.value = "EndDate format is wrong"
            }

            MainUiEvent.EndTimeInputFieldErrorMessage -> {
                endTimeInputFieldErrorMessage.value = "EndTime format is wrong"
            }
        }
    }
}

data class TimeOffRequestFormState(
    val startDate: String ="",
    val startTime: String = "",
    val endDate: String = "",
    val endTime: String ="",
    val classificationOfTimeOff : String ="",
    val classificationOfTimeOffDetails: String ="",
    val requestReason: String = "",
    val agentName: String ="",
    val emergencyNumber: String=""
)

sealed class MainUiEvent {
    data class StartDateChanged(val startDate: String) : MainUiEvent()
    data class StartDateInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class StartTimeChanged(val startTime: String) : MainUiEvent()
    data class StartTimeInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class EndDateChanged(val endDate: String) : MainUiEvent()
    data class EndDateInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class EndTimeChanged(val endTime: String) : MainUiEvent()
    data class EndTimeInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class ClassificationOfTimeOffExpanded(val expanded: Boolean): MainUiEvent()
    data class ClassificationOfTimeOffDetailsExpanded(val expanded: Boolean): MainUiEvent()
    data class TimeOffRequestReasonChanged(val reason: String) : MainUiEvent()
    data class AgentNameChanged(val agentName: String) : MainUiEvent()
    data class EmergencyNumberChanged(val emergencyNumber: String) : MainUiEvent()
    object StartDateInputFieldErrorMessage : MainUiEvent()
    object StartTimeInputFieldErrorMessage : MainUiEvent()
    object EndDateInputFieldErrorMessage : MainUiEvent()
    object EndTimeInputFieldErrorMessage : MainUiEvent()
}