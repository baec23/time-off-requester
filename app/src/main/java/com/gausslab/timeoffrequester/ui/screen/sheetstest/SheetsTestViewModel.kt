package com.gausslab.timeoffrequester.ui.screen.sheetstest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.service.FormSheetService
import com.gausslab.timeoffrequester.service.UsageSheetService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SheetsTestViewModel @Inject constructor(
    private val usageSheetService: UsageSheetService,
    private val formSheetService: FormSheetService
) : ViewModel() {
    fun onEvent(event: SheetsTestUiEvent) {
        when (event) {
            SheetsTestUiEvent.AddToUsagePressed -> addToUsage()
            SheetsTestUiEvent.AddToFormPressed -> addToForm()
            SheetsTestUiEvent.AddToBothPressed -> addToBoth()
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
}