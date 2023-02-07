package com.gausslab.timeoffrequester.ui.screen.requestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gausslab.timeoffrequester.model.TimeOffRequest2
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository,
) : ViewModel() {
    val currUser = userRepository.currUser!!

    private val _myTimeOffRequestList = MutableStateFlow<List<TimeOffRequest2>>(listOf())
    val myTimeOffRequestList = _myTimeOffRequestList.asStateFlow()

    fun onEvent(event: RequestListUiEvent) {
        when (event) {
            is RequestListUiEvent.RequestDetailClicked -> {
                //TODO: DETAILS 화면으로 넘어가기
            }
        }
    }

    private fun getTimeOffRequestListByUser(){
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getTimeOffRequestsByUser(userEmail)
            if(response.isSuccessful){
                _myTimeOffRequestList.value = response.body()!!
            }
        }
    }

    init {
        getTimeOffRequestListByUser()
    }
}

sealed class RequestListUiEvent {
    data class RequestDetailClicked(val timeOffRequest: TimeOffRequest2) : RequestListUiEvent()
}

