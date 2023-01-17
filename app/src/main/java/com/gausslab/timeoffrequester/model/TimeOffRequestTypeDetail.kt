package com.gausslab.timeoffrequester.model

enum class TimeOffRequestTypeDetail {
    FUNERAL_LEAVE,
    MARRIAGE_LEAVE,
    OTHER,
}

fun TimeOffRequestTypeDetail.toKorean(): String{
    var returnValue: String=""
    if (this==TimeOffRequestTypeDetail.FUNERAL_LEAVE){
        returnValue = "조의"
    }else if(this==TimeOffRequestTypeDetail.MARRIAGE_LEAVE){
        returnValue = "결혼"
    }else if(this==TimeOffRequestTypeDetail.OTHER){
        returnValue = "기타"
    }
    return returnValue
}