package com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.UserSavedDefaults
import com.gausslab.timeoffrequester.model.TimeOffRequestTypeDetail
import com.gausslab.timeoffrequester.model.toKorean
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.service.SnackbarService
import com.gausslab.timeoffrequester.ui.screen.myprofile.navigateToMyProfileScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDetailsInfoEditViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository,
    private val snackbarService: SnackbarService,
    private val navController: NavHostController
) : ViewModel() {
    val currUser = userRepository.currUser!!

    private val _reasonText = MutableStateFlow("개인사유")
    val reasonText = _reasonText.asStateFlow()

    private val _agentName = MutableStateFlow("홍길동")
    val agentName = _agentName.asStateFlow()

    private val _emergencyNumber = MutableStateFlow("01012341234")
    val emergencyNumber = _emergencyNumber.asStateFlow()

    private val _timeOffRequestTypeDetails = MutableStateFlow(TimeOffRequestTypeDetail.OTHER)
    val timeOffRequestTypeDetails = _timeOffRequestTypeDetails.asStateFlow()

    val timeOffRequestTypeDetailsExpanded: MutableState<Boolean> = mutableStateOf(false)

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()

    fun onEvent(event: MyDetailsInfoEditUiEvent) {
        when (event) {
            is MyDetailsInfoEditUiEvent.OnReasonChanged ->{
                _reasonText.value = event.reason
            }
            is MyDetailsInfoEditUiEvent.AgentNameChanged ->{
                _agentName.value = event.agentName
            }
            is MyDetailsInfoEditUiEvent.EmergencyNumberChanged -> {
                _emergencyNumber.value = event.emergencyNumber
            }
            is MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetails ->{
                _timeOffRequestTypeDetails.value = event.type
            }

            is MyDetailsInfoEditUiEvent.TimeOffRequestTypeDetailsExpanded ->{
                timeOffRequestTypeDetailsExpanded.value = event.expanded
            }

            MyDetailsInfoEditUiEvent.OnSubmitPressed -> {
                _isBusy.value = true
                val toSubmit = additionalInfo()
                viewModelScope.launch{
                    torApi.saveUserSavedDefaults(toSubmit)
                    _isBusy.value = false
                    navController.navigateToMyProfileScreen()
                }
            }
        }
    }

    private fun additionalInfo(): UserSavedDefaults{
        val userEmail = userRepository.currUser!!.email
        return UserSavedDefaults(
            userEmail = userEmail,
            reason = _reasonText.value,
            agentName = _agentName.value,
            emergencyNumber = _emergencyNumber.value,
            typeDetail = _timeOffRequestTypeDetails.value
        )
    }

    private fun getUserSavedDefaults(){
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getUserSavedDefaults(userEmail)
            if (response.isSuccessful){
                _reasonText.value=response.body()!!.reason
                _agentName.value = response.body()!!.agentName
                _emergencyNumber.value = response.body()!!.emergencyNumber
                _timeOffRequestTypeDetails.value = response.body()!!.typeDetail
            }else{
                snackbarService.showSnackbar("getUserSavedDefaults torApi response error")
                Log.d("DEBUG", "getUserSavedDefaults: torApi response error")
            }
        }
    }

    init{
        getUserSavedDefaults()
    }
}

sealed class MyDetailsInfoEditUiEvent {
    data class OnReasonChanged(val reason: String) : MyDetailsInfoEditUiEvent()
    data class AgentNameChanged(val agentName: String) : MyDetailsInfoEditUiEvent()
    data class EmergencyNumberChanged(val emergencyNumber: String) : MyDetailsInfoEditUiEvent()
    data class TimeOffRequestTypeDetails(val type: TimeOffRequestTypeDetail) :MyDetailsInfoEditUiEvent()
    data class TimeOffRequestTypeDetailsExpanded(val expanded: Boolean) : MyDetailsInfoEditUiEvent()
    object OnSubmitPressed : MyDetailsInfoEditUiEvent()
}