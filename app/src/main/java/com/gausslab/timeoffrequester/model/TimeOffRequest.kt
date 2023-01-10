package com.gausslab.timeoffrequester.model

data class TimeOffRequest(
    val status: String ="",
    val username: String ="",
    val position: String ="",
    val userStartDate: String ="",

    val startDate: String ="",
    val startTime: String = "",
    val endDate : String ="",
    val endTime : String ="",
    val timeOffRequestType: TimeOffRequestType,
    val timeOffRequestTypeDetails: TimeOffRequestTypeDetail,
    val requestReason: String ="",
    val agentName: String?="",
    val emergencyNumber: String? = "",
)
