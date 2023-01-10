package com.gausslab.timeoffrequester.ui.requestlist

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.requestdetails.navigateToRequestDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {

    fun onEvent(event: RequestListUiEvent){
        when(event){
            RequestListUiEvent.RequestDetailClicked -> navController.navigateToRequestDetailsScreen()
        }
    }

}

sealed class RequestListUiEvent{
    object RequestDetailClicked : RequestListUiEvent()
}