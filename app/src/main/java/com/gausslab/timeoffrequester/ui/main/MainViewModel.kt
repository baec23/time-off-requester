package com.gausslab.timeoffrequester.ui.main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {
    fun onEvent(event: MainUiEvent) {
        when(event){
            MainUiEvent.GoToLoginScreenPressed -> navController.navigateToLoginScreen()
        }
    }
}

sealed class MainUiEvent {
    object GoToLoginScreenPressed : MainUiEvent()
}