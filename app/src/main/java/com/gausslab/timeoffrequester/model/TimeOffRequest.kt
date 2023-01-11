package com.gausslab.timeoffrequester.model

data class TimeOffRequest(
    val status: String ="",
    val userId: String ="",
    val username: String ="",
    val position: String ="",
    val userStartDate: String ="",

    var timeOffRequestId: Int =0,
    val startDate: String ="",
    val startTime: String = "",
    val endDate : String ="",
    val endTime : String ="",
    val timeOffRequestType: TimeOffRequestType = TimeOffRequestType.ANNUAL_LEAVE,
    val timeOffRequestTypeDetails: TimeOffRequestTypeDetail = TimeOffRequestTypeDetail.OTHER,
    val requestReason: String ="",
    val agentName: String?="",
    val emergencyNumber: String? = "",
)
