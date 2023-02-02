package com.gausslab.timeoffrequester.ui.screen.requestform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest2
import com.gausslab.timeoffrequester.model.TimeOffRequestType
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RequestFormViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _selectedStartDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedStartDate = _selectedStartDate.asStateFlow()
    private val _selectedEndDate = MutableStateFlow<LocalDate>(LocalDate.now().plusDays(1))
    val selectedEndDate = _selectedEndDate.asStateFlow()

    private val _selectedStartTime =
        MutableStateFlow<LocalTime>(LocalDate.now().atStartOfDay().toLocalTime())
    val selectedStartTime = _selectedStartTime.asStateFlow()
    private val _selectedEndTime =
        MutableStateFlow<LocalTime>(LocalDate.now().atStartOfDay().toLocalTime())
    val selectedEndTime = _selectedEndTime.asStateFlow()

    private val _reasonText = MutableStateFlow("개인사유")
    val reasonText = _reasonText.asStateFlow()

    private val _remainingTimeOffRequests = MutableStateFlow("N/A")
    val remainingTimeOffRequests = _remainingTimeOffRequests.asStateFlow()

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()

    fun onEvent(event: RequestFormUiEvent) {
        when (event) {
            is RequestFormUiEvent.OnSelectedStartDateChanged -> _selectedStartDate.value =
                event.startDate

            is RequestFormUiEvent.OnSelectedStartTimeChanged -> _selectedStartTime.value =
                event.startTime

            is RequestFormUiEvent.OnSelectedEndDateChanged -> _selectedEndDate.value = event.endDate
            is RequestFormUiEvent.OnSelectedEndTimeChanged -> _selectedEndTime.value = event.endTime
            is RequestFormUiEvent.OnReasonChanged -> _reasonText.value = event.reason
            RequestFormUiEvent.OnSubmitPressed -> {
                _isBusy.value = true
                val toSubmit = generateTimeOffRequest()
                viewModelScope.launch {
                    torApi.submitTimeOffRequest(toSubmit)
                    updateRemainingTimeOffRequests()
                    _isBusy.value = false
                }
            }

            RequestFormUiEvent.OnSubmitPressed -> {
            }
        }
    }

    private fun generateTimeOffRequest(): TimeOffRequest2 {
        val userEmail = userRepository.currUser!!.email
        return TimeOffRequest2(
            userEmail = userEmail,
            startDateTime = LocalDateTime.of(_selectedStartDate.value, _selectedStartTime.value),
            endDateTime = LocalDateTime.of(_selectedEndDate.value, _selectedEndTime.value),
            type = TimeOffRequestType.DAY_LEAVE.toKorean(),
            detailedType = TimeOffRequestTypeDetail.OTHER.toKorean(),
            reason = _reasonText.value,
        )
    }

    private fun updateRemainingTimeOffRequests() {
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getRemainingTimeOffRequests(userEmail)
            if (response.isSuccessful) {
                _remainingTimeOffRequests.value = response.body()!!
            }
        }
    }

    init {
        updateRemainingTimeOffRequests()
    }
}

private fun LocalDate.toTorString(): String {
    return format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

private fun LocalTime.toTorString(): String {
    return format(DateTimeFormatter.ofPattern("HH:mm"))
}

sealed class RequestFormUiEvent {
    data class OnSelectedStartDateChanged(val startDate: LocalDate) : RequestFormUiEvent()
    data class OnSelectedStartTimeChanged(val startTime: LocalTime) : RequestFormUiEvent()
    data class OnSelectedEndDateChanged(val endDate: LocalDate) : RequestFormUiEvent()
    data class OnSelectedEndTimeChanged(val endTime: LocalTime) : RequestFormUiEvent()
    data class OnReasonChanged(val reason: String) : RequestFormUiEvent()
    object OnSubmitPressed : RequestFormUiEvent()
}