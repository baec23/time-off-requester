package com.gausslab.timeoffrequester.model

import android.widget.Switch

enum class TimeOffRequestType {
    ANNUAL_LEAVE,
    HALF_LEAVE,
    SICK_LEAVE,
    MATERNITY_LEAVE,
    CC_LEAVE,
    MENSTRUATION_LEAVE,
    PATERNITY_LEAVE,
    PUBLIC_LEAVE,;

}

fun TimeOffRequestType.toKorean(): String{
    var returnValue: String=""
    if (this==TimeOffRequestType.ANNUAL_LEAVE){
        returnValue = "연차"
    }else if(this==TimeOffRequestType.HALF_LEAVE){
        returnValue = "반차"
    }else if(this==TimeOffRequestType.SICK_LEAVE){
        returnValue = "병가"
    }else if(this==TimeOffRequestType.MATERNITY_LEAVE){
        returnValue = "출산전후휴가"
    }else if(this==TimeOffRequestType.CC_LEAVE){
        returnValue = "경조휴가"
    }else if(this==TimeOffRequestType.MENSTRUATION_LEAVE){
        returnValue = "생리휴가"
    }else if(this==TimeOffRequestType.PATERNITY_LEAVE){
        returnValue = "배우자출산휴가"
    }else if(this==TimeOffRequestType.PUBLIC_LEAVE){
        returnValue = "공가"
    }
    return returnValue
}