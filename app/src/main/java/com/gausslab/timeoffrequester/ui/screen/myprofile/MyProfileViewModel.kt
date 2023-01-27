package com.gausslab.timeoffrequester.ui.screen.myprofile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.navigateToMyProfileDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel(){

    val currUser = userRepository.currUser!!

    fun onEvent(event: MyProfileUiEvent){
        when(event){
            MyProfileUiEvent.MyProfileDetailsPressed -> {
                navController.navigateToMyProfileDetailsScreen(userId = currUser.id)
            }
        }
    }
}

sealed class MyProfileUiEvent{
    object MyProfileDetailsPressed : MyProfileUiEvent()
}