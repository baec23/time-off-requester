package com.gausslab.timeoffrequester.remote.model

import java.time.LocalDate
data class User(
    val email: String,
    val position: String,
    val team: String,
    val displayName: String,
    val hiredDate: LocalDate,
    val birthday: LocalDate
)
