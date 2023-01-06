package com.gausslab.timeoffrequester.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    private val _startDateState: MutableState<String> = mutableStateOf("")
    val startDateState: State<String> = _startDateState
    val startDateInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val startDateInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    private val _startTimeState: MutableState<String> = mutableStateOf("")
    val startTimeState: State<String> = _startTimeState
    val startTimeInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val startTimeInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    private val _endDateState: MutableState<String> = mutableStateOf("")
    val endDateState: State<String> = _endDateState
    val endDateInputFieldHasError: MutableState<Boolean> = mutableStateOf(true)
    val endDateInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")



    fun onEvent(event: MainUiEvent) {
        when (event) {
            MainUiEvent.GoToLoginScreenPressed -> navController.navigateToLoginScreen()
            is MainUiEvent.StartDateChanged -> {
                _startDateState.value = event.startDate
            }

            is MainUiEvent.StartDateInputFieldHasError -> {
                startDateInputFieldHasError.value = !startDateInputFieldHasError.value
            }

            is MainUiEvent.StartTimeChanged -> {
                _startTimeState.value = event.startTime
            }

            is MainUiEvent.StartTimeInputFieldHasError -> {
                startTimeInputFieldHasError.value = !startTimeInputFieldHasError.value
            }

            is MainUiEvent.EndDateChanged -> {
                _endDateState.value = event.endDate
            }

            is MainUiEvent.EndDateInputFieldHasError -> {
                endDateInputFieldHasError.value = !endDateInputFieldHasError.value
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


        }
    }
}

sealed class MainUiEvent {
    data class StartDateChanged(val startDate: String) : MainUiEvent()
    data class StartDateInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class StartTimeChanged(val startTime: String) : MainUiEvent()
    data class StartTimeInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    data class EndDateChanged(val endDate: String) : MainUiEvent()
    data class EndDateInputFieldHasError(val hasError: Boolean) : MainUiEvent()
    object StartDateInputFieldErrorMessage : MainUiEvent()
    object StartTimeInputFieldErrorMessage : MainUiEvent()
    object EndDateInputFieldErrorMessage : MainUiEvent()
    object GoToLoginScreenPressed : MainUiEvent()
}