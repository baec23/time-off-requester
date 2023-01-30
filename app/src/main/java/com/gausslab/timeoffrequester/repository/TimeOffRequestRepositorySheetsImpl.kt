package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.service.SheetsService
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val timeOffRequestUsageSheetId = "1OOfHRODinFkJ5E1d77n3DAIpPS_jLCt3ODB4XMa3SS4"
private const val timeOffRequestFormSheetId = ""

@ActivityScoped
class TimeOffRequestRepositorySheetsImpl @Inject constructor(private val sheetsService: SheetsService) :
    TimeOffRequestRepository {
    override fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest) {
        TODO("Not yet implemented")
        //연차 사용 내역 목록 작성
        //연차신청서 작성
    }

    override fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>> {
        TODO("Not yet implemented")
        //연차신청서에서 불러옴
    }

    override suspend fun getTimeOffRequestById(timeOffRequestId: String): TimeOffRequest {
        TODO("Not yet implemented")
        //연차신청서에서 불러옴
    }
}