package com.gausslab.timeoffrequester.util

object API{
    const val BASE_URL: String = "https://docs.google.com/spreadsheets/d/"

    const val CLIENT_ID: String = "7be58b8c54670cb5c87ae8e9a4a36dd2d5274708"

    const val READ_SHEET : String = "1GXf3RkQpJX1DWJDSVyU-kRGxxKQ-pr0vRvX87jRrVGk"
}

enum class RESPONSE_STATE{
    OK,
    FAIL
}