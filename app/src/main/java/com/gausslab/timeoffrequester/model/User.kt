package com.gausslab.timeoffrequester.model

data class User(
    val id:String ="",
    val password: String ="",
    val username:String ="",
    val position:String="",
    val startDate:String="",
    val partName:String ="",
    val remainingTimeOffRequests:Int =0
)
