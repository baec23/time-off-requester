package com.gausslab.timeoffrequester.datainterface

import com.gausslab.timeoffrequester.model.TimeOffRequest

interface TimeOffRequestRepository {
    fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest)
    fun getAllTimeOffRequests()
    fun getTImeOFfRequestById(timeOffRequestId: String)
}