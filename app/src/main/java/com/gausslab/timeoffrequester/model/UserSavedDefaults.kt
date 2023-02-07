package com.gausslab.timeoffrequester.model

data class UserSavedDefaults(
    val userEmail:String ="",
    val reason:String="",
    val agentName:String ="",
    val emergencyNumber:String ="",
    val typeDetail: TimeOffRequestTypeDetail
)
