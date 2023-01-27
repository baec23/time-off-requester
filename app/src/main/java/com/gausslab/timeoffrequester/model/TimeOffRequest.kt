package com.gausslab.timeoffrequester.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class TimeOffRequest(
    val status: String = "",
    val userId: String = "",
    val username: String = "",
    val position: String = "",
    val userStartDate: String = "",
    var timeOffRequestId: Int = 0,
    val startDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val startTime: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
    val endDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val endTime: String=  LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
    val timeOffRequestType: TimeOffRequestType = TimeOffRequestType.ANNUAL_LEAVE,
    val timeOffRequestTypeDetails: TimeOffRequestTypeDetail = TimeOffRequestTypeDetail.OTHER,
    val requestReason: String = "",
    val agentName: String? = "",
    val emergencyNumber: String? = "",
)
