package com.gausslab.timeoffrequester.ui.requestdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepository
import com.gausslab.timeoffrequester.ui.requestlist.navigateToRequestListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val timeOffRequestRepository: TimeOffRequestRepository,
    private val navController: NavHostController
) : ViewModel() {

    private val _currTimeOffRequest = MutableStateFlow<TimeOffRequest>(TimeOffRequest())
    val currTimeOffRequest = _currTimeOffRequest

    fun onEvent(event: RequestDetailsUiEvent) {
        when (event) {
            RequestDetailsUiEvent.ConfirmButtonPressed -> navController.navigateToRequestListScreen()
        }
    }

    init {
        viewModelScope.launch {
            timeOffRequestRepository.getAllTimeOffRequests().collect {
                val filteredList = it.filter { timeOffRequest ->
                    timeOffRequest.timeOffRequestId==timeOffRequestRepository.getTimeOffRequestId()
                }
                _currTimeOffRequest.emit(filteredList.first())
            }
        }
    }
}

sealed class RequestDetailsUiEvent {
    object ConfirmButtonPressed : RequestDetailsUiEvent()
}