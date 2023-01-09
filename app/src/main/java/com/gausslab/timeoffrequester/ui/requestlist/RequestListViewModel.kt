package com.gausslab.timeoffrequester.ui.requestlist

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {

    fun onEvent(event: RequestListUiEvent){
//        when(event){
//
//        }
    }

}

sealed class RequestListUiEvent{

}