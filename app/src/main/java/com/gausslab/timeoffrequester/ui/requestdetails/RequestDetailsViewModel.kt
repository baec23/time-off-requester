package com.gausslab.timeoffrequester.ui.requestdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.ui.requestlist.navigateToRequestListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val timeOffRequestRepository: TimeOffRequestRepository,
    private val navController: NavHostController
) : ViewModel() {

    private val _currTimeOffRequest = MutableStateFlow(TimeOffRequest())
    val currTimeOffRequest = _currTimeOffRequest
    val expandableSessionState: MutableState<Boolean> = mutableStateOf(false)

    fun onEvent(event: RequestDetailsUiEvent) {
        when (event) {
            RequestDetailsUiEvent.ConfirmButtonPressed -> navController.navigateToRequestListScreen()
            RequestDetailsUiEvent.ExpandableSessionPressed -> {
                expandableSessionState.value = !expandableSessionState.value
            }
        }
    }

    fun setCurrTimeOffRequest(timeOffRequestId: String) {
        viewModelScope.launch {
            _currTimeOffRequest.emit(
                timeOffRequestRepository.getTimeOffRequestById(timeOffRequestId = timeOffRequestId)
            )
        }
    }
}

sealed class RequestDetailsUiEvent {
    object ConfirmButtonPressed : RequestDetailsUiEvent()
    object ExpandableSessionPressed : RequestDetailsUiEvent()
}