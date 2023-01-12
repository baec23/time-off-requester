package com.gausslab.timeoffrequester.ui.requestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepository
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.requestdetails.navigateToRequestDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val timeOffRequestRepository: TimeOffRequestRepository,
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    private val _myTimeOffRequests = MutableStateFlow<List<TimeOffRequest>>(listOf())
    val myTimeOffRequests = _myTimeOffRequests.asStateFlow()

    fun onEvent(event: RequestListUiEvent) {
        when (event) {
            is RequestListUiEvent.RequestDetailClicked -> {
                navController.navigateToRequestDetailsScreen(timeOffRequestId = event.timeOffRequest.timeOffRequestId)
            }
        }
    }

    init {
        viewModelScope.launch {
            timeOffRequestRepository.getAllTimeOffRequests().collect{
                val filteredList=it.filter { timeOffRequest ->
                    timeOffRequest.userId == userRepository.currUser!!.id
                }
                _myTimeOffRequests.emit(filteredList)
            }
        }
    }

}

sealed class RequestListUiEvent {
    data class RequestDetailClicked(val timeOffRequest: TimeOffRequest) : RequestListUiEvent()
}