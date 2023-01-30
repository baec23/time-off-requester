package com.gausslab.timeoffrequester.ui.screen.sheetstest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.service.FormSheetService
import com.gausslab.timeoffrequester.service.GmailService
import com.gausslab.timeoffrequester.service.UsageSheetService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SheetsTestViewModel @Inject constructor(
    private val usageSheetService: UsageSheetService,
    private val formSheetService: FormSheetService,
    private val gmailService: GmailService,
) : ViewModel() {
    private val _recipientEmail = MutableStateFlow("")
    val recipientEmail = _recipientEmail.asStateFlow()
    private val _emailSectionExpanded = MutableStateFlow(false)
    val emailSectionExpanded = _emailSectionExpanded.asStateFlow()

    fun onEvent(event: SheetsTestUiEvent) {
        when (event) {
            SheetsTestUiEvent.AddToUsagePressed -> addToUsage()
            SheetsTestUiEvent.AddToFormPressed -> addToForm()
            SheetsTestUiEvent.AddToBothPressed -> addToBoth()
            SheetsTestUiEvent.DoAllPressed -> doAll()
            SheetsTestUiEvent.EmailSectionPressed -> _emailSectionExpanded.value =
                !_emailSectionExpanded.value

            is SheetsTestUiEvent.RecipientEmailChanged -> _recipientEmail.value = event.email
        }
    }

    private fun addToUsage() {
        viewModelScope.launch {
            usageSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
        }
    }

    private fun addToForm() {
        viewModelScope.launch {
            formSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
        }
    }

    private fun addToBoth() {
        viewModelScope.launch {
            formSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
            usageSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
        }
    }

    private fun doAll() {
        viewModelScope.launch {
            formSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
            usageSheetService.addTimeOffRequest(generateRandomTimeOffRequest())
            gmailService.sendEmail(
                recipientEmail = _recipientEmail.value,
                subjectText = "TEST SUBJECT",
                bodyText = "THIS IS THE TEST BODY"
            )
        }
    }

    private fun generateRandomTimeOffRequest(): TimeOffRequest {
        return TimeOffRequest(
            username = "홍길동",
            position = "팀장",
            userStartDate = "2022-01-01",
            startDate = "2023-02-03",
            startTime = "09:30",
            endDate = "2023-02-03",
            endTime = "18:30",
            requestReason = "개인사유이유이유",
            agentName = "김철수",
            emergencyNumber = "010-1234-5678"
        )
    }
}

sealed class SheetsTestUiEvent {
    object AddToUsagePressed : SheetsTestUiEvent()
    object AddToFormPressed : SheetsTestUiEvent()
    object AddToBothPressed : SheetsTestUiEvent()
    object DoAllPressed : SheetsTestUiEvent()
    object EmailSectionPressed : SheetsTestUiEvent()
    data class RecipientEmailChanged(val email: String) : SheetsTestUiEvent()
}