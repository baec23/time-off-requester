package com.gausslab.timeoffrequester.service

import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.model.toKorean
import dagger.hilt.android.scopes.ActivityScoped
import java.time.LocalDate
import javax.inject.Inject

const val formSheetId = "1Wvf1fghbNdjiHkI_m_jQbwF58bMbf1OX4tdWKOXUQhE"
const val baseSheetTitle = "연차신청서 원본(복사해서 사용) 탭제목: 이름_연차사용일"

const val positionRange = "V9:AD9"
const val nameRange = "F10:P10"
const val hiredDateRange = "V10:AD10"
const val typeRange = "F11:AD11"
const val miscTypeRange = "F12:AD12"
const val reasonRange = "F13:AD13"
const val startYearRange = "F14:G14"
const val startMonthRange = "I14:J14"
const val startDayRange = "L14:M14"
const val startHoursRange = "O14:P14"
const val startMinutesRange = "R14:S14"
const val endYearRange = "F15:G15"
const val endMonthRange = "I15:J15"
const val endDayRange = "L15:M15"
const val endHoursRange = "O15:P15"
const val endMinutesRange = "R15:S15"
const val emergencyContactNameRange = "F16:AD16"
const val emergencyContactNumberRange = "F17:AD17"
const val formDateRange = "A23:AD23"
const val teamNameRange = "R25:AD25"
const val signatureNameRange = "R26:AB26"

@ActivityScoped
class FormSheetService @Inject constructor(
    private val sheetsService: SheetsService
) {
    suspend fun addTimeOffRequest(timeOffRequest: TimeOffRequest) {

        val formattedStartDate = "230203" //TODO: Format Properly

        val newSheetTitle = "${timeOffRequest.username}_$formattedStartDate"
        duplicateSheet(newSheetTitle)
        updateSheetValues(newSheetTitle, timeOffRequest = timeOffRequest)
    }

    private suspend fun duplicateSheet(newSheetTitle: String) {
        sheetsService.duplicateSheetWithinSpreadsheet(
            spreadsheetId = formSheetId,
            sourceSheetTitle = baseSheetTitle,
            newSheetTitle = newSheetTitle
        )
    }

    private suspend fun updateSheetValues(newSheetTitle: String, timeOffRequest: TimeOffRequest) {
        val cells = listOf(
            positionRange,
            nameRange,
            hiredDateRange,
            typeRange,
            miscTypeRange,
            reasonRange,
            startYearRange,
            startMonthRange,
            startDayRange,
            startHoursRange,
            startMinutesRange,
            endYearRange,
            endMonthRange,
            endDayRange,
            endHoursRange,
            endMinutesRange,
            emergencyContactNameRange,
            emergencyContactNumberRange,
            formDateRange,
            teamNameRange,
            signatureNameRange
        )
        val values = with(timeOffRequest) {

            val startYear = "23" //TODO: Parse date/time into strings
            val startMonth = "02"
            val startDay = "03"
            val startHours = "09"
            val startMinutes = "30"

            val endYear = "23" //TODO: Parse date/time into strings
            val endMonth = "02"
            val endDay = "03"
            val endHours = "09"
            val endMinutes = "30"

            val todayDate = LocalDate.now()
            val todayDateValue = "${todayDate.year}년 ${todayDate.monthValue}월 ${todayDate.dayOfMonth}일"

            val teamName = "DT" //TODO: Get user's team

            listOf(
                position,
                username,
                userStartDate,
                timeOffRequestType.toKorean(),  //TODO: Keep original formatting
                timeOffRequestTypeDetails.toKorean(), //TODO: Keep original formatting
                requestReason,
                startYear,
                startMonth,
                startDay,
                startHours,
                startMinutes,
                endYear,
                endMonth,
                endDay,
                endHours,
                endMinutes,
                agentName ?: "",
                emergencyNumber ?: "",
                todayDateValue,
                teamName,
                username
            )
        }

        sheetsService.updateValuesForCells(
            spreadsheetId = formSheetId,
            sheetTitle = newSheetTitle,
            cells = cells,
            values = values
        )
    }
}