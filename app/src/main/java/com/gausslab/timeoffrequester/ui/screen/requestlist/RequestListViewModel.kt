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

    val testList = TimeOffRequest2(
        id = "dawn@gausslab.co.kr",
        status = "승인대기",
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now(),
        type = "반차",
        detailedType = "",
        reason = "개인사유",
        userEmail = "dawn@gausslab.co.kr",
    )



    fun onEvent(event: RequestListUiEvent) {
        when (event) {
            is RequestListUiEvent.RequestDetailClicked -> {
                //TODO: DETAILS 화면으로 넘어가기
            }
        }
    }

    init {
        viewModelScope.launch {
            //TODO: 리스트 불러오기
            _myTimeOffRequestList.value= listOf(testList)
        }
    }
}

sealed class RequestListUiEvent {
    data class RequestDetailClicked(val timeOffRequest: TimeOffRequest2) : RequestListUiEvent()
}

