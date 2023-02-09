package com.gausslab.timeoffrequester.ui.screen.requestlist

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.service.SnackbarService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val torApi: TorApi,
    private val snackbarService: SnackbarService,
    private val userRepository: UserRepository,
    private val application: Application,
) : ViewModel() {
    val currUser = userRepository.currUser!!

    private val _myTimeOffRequestList = MutableStateFlow<List<TimeOffRequest>>(listOf())
    val myTimeOffRequestList = _myTimeOffRequestList.asStateFlow()

    fun onEvent(event: RequestListUiEvent) {
        when (event) {
            is RequestListUiEvent.RequestDetailClicked -> {
                val url = "https://docs.google.com/spreadsheets/d/1Wvf1fghbNdjiHkI_m_jQbwF58bMbf1OX4tdWKOXUQhE/edit#gid=${event.timeOffRequest.id}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(application.applicationContext, intent.addFlags(FLAG_ACTIVITY_NEW_TASK),null)
            }
        }
    }
    private fun getTimeOffRequestListByUser(){
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getTimeOffRequestsByUser(userEmail)
            if(response.isSuccessful){
                _myTimeOffRequestList.value = response.body()!!
            }else{
                snackbarService.showSnackbar("getTimeOffRequestListByUser torApi response error")
                Log.d("DEBUG", "getTimeOffRequestListByUser: torApi response error")
            }
        }
    }

    init {
        getTimeOffRequestListByUser()
    }
}

sealed class RequestListUiEvent {
    data class RequestDetailClicked(val timeOffRequest: TimeOffRequest) : RequestListUiEvent()
}

