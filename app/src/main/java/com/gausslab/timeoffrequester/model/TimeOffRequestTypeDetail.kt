package com.gausslab.timeoffrequester.model

enum class TimeOffRequestTypeDetail {
    OTHER,
    FUNERAL_LEAVE,
    MARRIAGE_LEAVE,
}

fun TimeOffRequestTypeDetail.toKorean(): String {
    var returnValue: String = ""
    if (this == TimeOffRequestTypeDetail.OTHER) {
        returnValue = "기타"
    } else if (this == TimeOffRequestTypeDetail.FUNERAL_LEAVE) {
        returnValue = "조의"
    } else if (this == TimeOffRequestTypeDetail.MARRIAGE_LEAVE) {
        returnValue = "결혼"
    }
    return returnValue
}