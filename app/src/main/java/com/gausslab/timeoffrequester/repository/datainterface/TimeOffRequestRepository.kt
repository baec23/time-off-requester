package com.gausslab.timeoffrequester.repository.datainterface

import com.gausslab.timeoffrequester.model.TimeOffRequest
import kotlinx.coroutines.flow.Flow

interface TimeOffRequestRepository {
    fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest)
    fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>>
    suspend fun getTimeOffRequestById(timeOffRequestId: String) : TimeOffRequest
}