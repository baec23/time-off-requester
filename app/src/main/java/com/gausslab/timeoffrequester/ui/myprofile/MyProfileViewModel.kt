package com.gausslab.timeoffrequester.ui.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.myprofiledetails.navigateToMyProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navController: NavHostController
) : ViewModel(){

    val currUser = userRepository.currUser!!

    fun onEvent(event: MyProfileUiEvent){
        when(event){
            MyProfileUiEvent.LogoutPressed -> {
                viewModelScope.launch {
                    dataStoreRepository.remove("savedUserId")
                    navController.navigateToLoginScreen()
                }
            }

            MyProfileUiEvent.MyProfileDetailsPressed -> {
                navController.navigateToMyProfileDetailsScreen(userId = currUser.id)
            }

            MyProfileUiEvent.ChangePasswordButtonPressed -> {
                //
            }
        }
    }
}

sealed class MyProfileUiEvent{
    object LogoutPressed : MyProfileUiEvent()
    object MyProfileDetailsPressed : MyProfileUiEvent()
    object ChangePasswordButtonPressed: MyProfileUiEvent()
}