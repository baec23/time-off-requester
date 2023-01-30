package com.gausslab.timeoffrequester.service

import android.content.Context
import com.gausslab.timeoffrequester.R
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.UpdateSheetPropertiesRequest
import com.google.api.services.sheets.v4.model.ValueRange
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class SheetsService @Inject constructor(
    private val context: Context,
    private val googleAuthService: GoogleAuthService
) {
    private var sheetsApi: Sheets? = null

    init {
        MainScope().launch {
            googleAuthService.signedInAccount.collect {
                if (it == null)
                    return@collect
                val scopes = listOf(
                    SheetsScopes.SPREADSHEETS
                )
                val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
                credential.selectedAccount = it.account
                val jsonFactory = GsonFactory()
                val httpTransport = NetHttpTransport()
                sheetsApi = Sheets.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(context.resources.getString(R.string.app_name))
                    .build()
            }
        }
    }

    suspend fun getValuesForRange(
        spreadsheetId: String,
        sheetTitle: String? = null,
        range: String
    ): List<List<String>> {
        if (sheetsApi == null)
            throw Exception()
        val rangeWithSheet = if (sheetTitle == null) range else "$sheetTitle!$range"

        return withContext(Dispatchers.IO) {
            val result =
                sheetsApi!!.spreadsheets().values().get(spreadsheetId, rangeWithSheet).execute()
            return@withContext result.getValues().map { row ->
                row.map { cell ->
                    cell.toString()
                }
            }
        }
    }

    suspend fun updateValuesForRange(
        spreadsheetId: String,
        sheetTitle: String? = null,
        range: String,
        values: List<List<String>>
    ) {
        if (sheetsApi == null)
            throw Exception()
        val rangeWithSheet = if (sheetTitle == null) range else "$sheetTitle!$range"

        withContext(Dispatchers.IO) {
            val content =
                ValueRange().setMajorDimension("ROWS").setRange(rangeWithSheet)
                    .setValues(values)
            sheetsApi!!.spreadsheets().values()
                .update(spreadsheetId, rangeWithSheet, content)
                .setValueInputOption("RAW").execute()
        }
    }

    suspend fun updateValuesForCells(
        spreadsheetId: String,
        sheetTitle: String? = null,
        cells: List<String>,
        values: List<String>
    ) {
        if (sheetsApi == null || cells.size != values.size)
            throw Exception()

        withContext(Dispatchers.IO) {
            val valueRangeList = mutableListOf<ValueRange>()

            cells.forEachIndexed { index, s ->
                val rangeWithSheet =
                    if (sheetTitle == null) cells[index] else "$sheetTitle!${cells[index]}"

                val currValueRange =
                    ValueRange().setMajorDimension("ROWS").setRange(rangeWithSheet)
                        .setValues(listOf(listOf(values[index])))
                valueRangeList.add(currValueRange)
            }
            val batchUpdateRequest = BatchUpdateValuesRequest()
            batchUpdateRequest.setData(valueRangeList.toList()).setValueInputOption("RAW")

            sheetsApi!!.spreadsheets().values().batchUpdate(spreadsheetId, batchUpdateRequest)
                .execute()
        }
    }

    suspend fun updateValueForCell(
        spreadsheetId: String,
        sheetTitle: String? = null,
        cell: String,
        value: String
    ) {
        updateValuesForRange(
            spreadsheetId = spreadsheetId,
            sheetTitle = sheetTitle,
            range = cell,
            values = listOf(listOf(value))
        )
    }

    suspend fun duplicateSheetWithinSpreadsheet(
        spreadsheetId: String,
        sourceSheetTitle: String,
        newSheetTitle: String,
    ) {
        if (sheetsApi == null)
            throw Exception()

        withContext(Dispatchers.IO) {
            val spreadsheet =
                sheetsApi!!.spreadsheets().get(spreadsheetId).execute()

            val sourceSheetIndex =
                spreadsheet.sheets.indexOfFirst { it.properties.title == sourceSheetTitle }
            val sourceSheetId = spreadsheet.sheets[sourceSheetIndex].properties.sheetId

            val result = sheetsApi!!
                .spreadsheets()
                .sheets()
                .copyTo(
                    spreadsheetId,
                    sourceSheetId,
                    CopySheetToAnotherSpreadsheetRequest()
                        .setDestinationSpreadsheetId(spreadsheetId)
                )
                .execute()

            result.title = newSheetTitle
            result.index = sourceSheetIndex + 1

            val batchRequest = BatchUpdateSpreadsheetRequest()

            val updatePropertiesRequest = Request()
            updatePropertiesRequest.updateSheetProperties =
                UpdateSheetPropertiesRequest().setProperties(result).setFields("*")
            batchRequest.requests = listOf(updatePropertiesRequest)

            sheetsApi!!.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute()
        }
    }
}