package com.gausslab.timeoffrequester.ui.screen.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit.navigateToMyDetailsInfoEditeScreen
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.navigateToMyProfileDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val torApi: TorApi,
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    val currUser = userRepository.currUser!!

    private val _remainingTimeOffRequests = MutableStateFlow("N/A")
    val remainingTimeOffRequests = _remainingTimeOffRequests.asStateFlow()

    fun onEvent(event: MyProfileUiEvent) {
        when (event) {
            MyProfileUiEvent.MyProfileDetailsPressed -> {
                navController.navigateToMyProfileDetailsScreen()
            }

            MyProfileUiEvent.MyDetailsInfoEditPressed -> {
                navController.navigateToMyDetailsInfoEditeScreen()
            }
        }
    }

    private fun updateRemainingTimeOffRequests() {
        val userEmail = userRepository.currUser!!.email
        viewModelScope.launch {
            val response = torApi.getRemainingTimeOffRequests(userEmail)
            if (response.isSuccessful) {
                _remainingTimeOffRequests.value = response.body()!!
            }
        }
    }

    init {
        updateRemainingTimeOffRequests()
    }
}

sealed class MyProfileUiEvent {
    object MyProfileDetailsPressed : MyProfileUiEvent()
    object MyDetailsInfoEditPressed : MyProfileUiEvent()
}