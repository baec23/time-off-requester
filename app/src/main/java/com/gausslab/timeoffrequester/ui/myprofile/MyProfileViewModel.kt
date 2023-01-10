package com.gausslab.timeoffrequester.ui.myprofile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel(){
    fun onEvent(event: MyProfileUiEvent){
//        when(event){
//
//        }
    }
}

sealed class MyProfileUiEvent{

}