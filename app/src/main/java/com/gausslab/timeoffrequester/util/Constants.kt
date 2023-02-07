package com.gausslab.timeoffrequester.util

object STRING{
    const val SAVED_USERID : String = "savedUserId"
}

enum class DetailsScreenRoute{
    myProfileDetails_screen_route,
    my_details_info_edit_screen_route,
}

fun DetailsScreenRoute.toKorean(): String{
    return when(this){
        DetailsScreenRoute.myProfileDetails_screen_route -> "내 정보 확인"
        DetailsScreenRoute.my_details_info_edit_screen_route -> "내 추가 정보 수정"
    }
}