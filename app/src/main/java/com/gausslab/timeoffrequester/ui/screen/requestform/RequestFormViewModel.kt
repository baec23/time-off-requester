package com.gausslab.timeoffrequester.ui.screen.requestform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.service.CalendarService
import com.gausslab.timeoffrequester.service.FormSheetService
import com.gausslab.timeoffrequester.service.GmailService
import com.gausslab.timeoffrequester.service.GoogleAuthService
import com.gausslab.timeoffrequester.service.UsageSheetService
import com.google.api.client.util.DateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RequestFormViewModel @Inject constructor(
    private val googleAuthService: GoogleAuthService,
    private val formSheetService: FormSheetService,
    private val usageSheetService: UsageSheetService,
    private val gmailService: GmailService,
    private val calendarService: CalendarService,
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

    private val _reasonText = MutableStateFlow<String>("개인사유")
    val reasonText = _reasonText.asStateFlow()

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
                val toSubmit = generateTimeOffRequest()
                viewModelScope.launch {
                    formSheetService.addTimeOffRequest(toSubmit)
                    usageSheetService.addTimeOffRequest(toSubmit)
                    gmailService.sendEmail("baec23@gmail.com", toSubmit.username, "linkUrl")
                    calendarService.createEvent(
                        summary = "[연차] $toSubmit.username", startDateTime = DateTime(
                            Date.from(
                                _selectedStartDate.value.atStartOfDay().toInstant(ZoneOffset.UTC)
                            )
                        ), endDateTime = DateTime(
                            Date.from(
                                _selectedEndDate.value.atStartOfDay().toInstant(ZoneOffset.UTC)
                            )
                        ), description = ""
                    )
                }
            }
        }
    }

    private fun generateTimeOffRequest(): TimeOffRequest {
        val signedInAccount = googleAuthService.signedInAccount.value ?: throw Exception()
        return TimeOffRequest(
            username = signedInAccount.displayName!!,
            startDate = _selectedStartDate.value.toTorString(),
            startTime = _selectedStartTime.value.toTorString(),
            endDate = _selectedEndDate.value.toTorString(),
            endTime = _selectedEndTime.value.toTorString(),
            requestReason = _reasonText.value
        )
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