package com.gausslab.timeoffrequester.service

import android.content.Context
import android.util.Log
import com.gausslab.timeoffrequester.R
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//private const val calendarId = "c_rrrud61i5p5ai5lebnbcndpb6g@group.calendar.google.com"
private const val calendarId = "primary"
private const val colorId = "5"

@ActivityScoped
class CalendarService constructor(
    private val context: Context,
    private val googleAuthService: GoogleAuthService
) {
    private var calendarApi: Calendar? = null

    init {
        MainScope().launch {
            googleAuthService.signedInAccount.collect {
                if (it == null)
                    return@collect
                val scopes = listOf(CalendarScopes.CALENDAR)
                val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
                credential.selectedAccount = it.account
                val jsonFactory = GsonFactory()
                val httpTransport = NetHttpTransport()

                calendarApi = Calendar.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(context.resources.getString(R.string.app_name))
                    .build()
            }
        }
    }

    suspend fun createEvent(
        summary: String,
        description: String,
        startDateTime: DateTime,
        endDateTime: DateTime
    ) {
        if (calendarApi == null)
            return
        withContext(Dispatchers.IO) {
            val event = Event()
            event.summary = summary
            event.description = description

            val startDate = EventDateTime()
            startDate.date = startDateTime
            startDate.timeZone = "Asia/Seoul"
            event.start = startDate

            val endDate = EventDateTime()
            endDate.date = endDateTime
            endDate.timeZone = "Asia/Seoul"
            event.end = endDate

            event.reminders = Event.Reminders().setUseDefault(false)
            event.colorId = colorId

            try {
                calendarApi!!.Events().insert(calendarId, event).execute()
            } catch (e: GoogleJsonResponseException) {
                val error = e.details
                if (e.statusCode == 403) {
                    Log.d("CALENDAR", "createEvent: unable to create event: $error")
                } else {
                    throw e
                }
            }
        }
    }
}