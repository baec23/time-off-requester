package com.gausslab.timeoffrequester.ui.requestdetails

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.requestlist.navigateToRequestListScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestDetailsViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel(){
    fun onEvent(event: RequestDetailsUiEvent){
        when (event){
            RequestDetailsUiEvent.ConfirmButtonPressed -> navController.navigateToRequestListScreen()
        }
    }
}

sealed class RequestDetailsUiEvent{
    object ConfirmButtonPressed : RequestDetailsUiEvent()
}