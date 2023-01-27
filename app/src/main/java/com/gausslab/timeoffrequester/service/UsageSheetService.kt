package com.gausslab.timeoffrequester.service

import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.model.toKorean
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

const val usageSheetId = "1OOfHRODinFkJ5E1d77n3DAIpPS_jLCt3ODB4XMa3SS4"
const val numColumn = "C"
const val nameColumn = "D"
const val positionColumn = "E"
const val reasonColumn = "F"
const val startDateColumn = "G"
const val endDateColumn = "H"
const val typeColumn = "I"
const val expendedColumn = "J"
const val noteColumn = "K"
const val firstContentRow = 15

@ActivityScoped
class UsageSheetService @Inject constructor(
    private val sheetsService: SheetsService
) {
    suspend fun addTimeOffRequest(timeOffRequest: TimeOffRequest) {
        val row = getFirstEmptyRow()
        val numValue = row - firstContentRow + 1

        val numExpended = 1 //TODO: Calculate based on date and time

        val requestValues = with(timeOffRequest) {
            listOf(
                listOf(
                    "$numValue",
                    username,
                    position,
                    requestReason,
                    startDate,
                    endDate,
                    timeOffRequestType.toKorean(),
                    "$numExpended",
                    timeOffRequestTypeDetails.toKorean() //Is this right?
                )
            )
        }
        val range = "C$row:K$row"
        sheetsService.updateValuesForRange(
            spreadsheetId = usageSheetId,
            range = range,
            values = requestValues
        )
    }

    private suspend fun getFirstEmptyRow(): Int {
        val results =
            sheetsService.getValuesForRange(spreadsheetId = usageSheetId, range = "$nameColumn$firstContentRow:$nameColumn${firstContentRow+1000}")
        return results.size + firstContentRow
    }
}