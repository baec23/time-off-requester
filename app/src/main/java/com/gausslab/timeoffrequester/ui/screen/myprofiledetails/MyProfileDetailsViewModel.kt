package com.gausslab.timeoffrequester.ui.screen.myprofiledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.remote.model.User
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    val currUser = userRepository.currUser!!

    fun onEvent(event: MyProfileDetailsUiEvent){
//        when(event){
//
//        }
    }

}

sealed class MyProfileDetailsUiEvent{

}