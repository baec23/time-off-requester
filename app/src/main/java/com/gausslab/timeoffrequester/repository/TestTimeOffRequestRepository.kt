package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.model.TimeOffRequest
import kotlinx.coroutines.flow.Flow

class TestTimeOffRequestRepository: TimeOffRequestRepository {
    override fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest) {
        TODO("Not yet implemented")
    }

    override fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTimeOffRequestById(timeOffRequestId: String): TimeOffRequest {
        TODO("Not yet implemented")
    }

}