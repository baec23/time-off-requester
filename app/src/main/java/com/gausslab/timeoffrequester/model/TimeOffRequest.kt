package com.gausslab.timeoffrequester.model

import java.time.LocalDateTime

data class TimeOffRequest(
    val id: String = "",
    val status: String = "",
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val type: String,
    val detailedType: String,
    val reason: String,
    val userEmail: String,
)
