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
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository,
) : ViewModel(){
     val currUser = userRepository.currUser!!

    private val _myTimeOffRequestList = MutableStateFlow<List<TimeOffRequest2>>(listOf())
    val myTimeOffRequestList = _myTimeOffRequestList.asStateFlow()

    fun onEvent(event: RequestListUiEvent){
        when (event){

            else -> {}
        }
    }

    init {
        viewModelScope.launch {
            //TODO: 리스트 불러오기
        }
    }
}

sealed class RequestListUiEvent{

}