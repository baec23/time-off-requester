package com.gausslab.timeoffrequester.model

enum class TimeOffRequestTypeDetail {
    OTHER,
    FUNERAL_LEAVE,
    MARRIAGE_LEAVE,
}

fun TimeOffRequestTypeDetail.toKorean(): String {
    return when(this){
        TimeOffRequestTypeDetail.OTHER -> "기타"
        TimeOffRequestTypeDetail.FUNERAL_LEAVE -> "조의"
        TimeOffRequestTypeDetail.MARRIAGE_LEAVE -> "결혼"
    }
}