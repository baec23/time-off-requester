package com.gausslab.timeoffrequester.ui.screen.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.UserRepositoryImpl
import com.gausslab.timeoffrequester.ui.screen.changepassword.navigateToChangePasswordScreen
import com.gausslab.timeoffrequester.ui.screen.myprofiledetails.navigateToMyProfileDetailsScreen
import com.gausslab.timeoffrequester.ui.screen.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

            MyProfileUiEvent.ChangePasswordButtonPressed -> {
                navController.navigateToChangePasswordScreen()
            }
        }
    }
}

sealed class MyProfileUiEvent{
    object MyProfileDetailsPressed : MyProfileUiEvent()
    object ChangePasswordButtonPressed: MyProfileUiEvent()
}