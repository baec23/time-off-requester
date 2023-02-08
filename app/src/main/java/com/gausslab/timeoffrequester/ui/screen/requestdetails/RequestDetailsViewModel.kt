package com.gausslab.timeoffrequester.ui.screen.requestdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _currTimeOffRequest = MutableStateFlow<List<TimeOffRequest>>(listOf())
    val currTimeOffRequest = _currTimeOffRequest.asStateFlow()

    fun onEvent(event: RequestDetailsUiEvent) {
        when (event) {
            else -> {}
        }
    }

    fun setCurrTimeOffRequest(timeOffRequestId: String) {
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getTimeOffRequestsByUser(userEmail)
            if (response.isSuccessful) {
                Log.d("asdfasdfasdf", "setCurrTimeOffRequest: "+response.body())
            }
        }
    }
}

sealed class RequestDetailsUiEvent {
    object ConfirmButtonPressed : RequestDetailsUiEvent()
}