package com.gausslab.timeoffrequester.ui.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.main.navigateToMainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {
    fun onEvent(event: LoginUiEvent) {
        when(event){
            LoginUiEvent.GoToMainScreenPressed -> navController.navigateToMainScreen()
        }
    }
}

sealed class LoginUiEvent {
    object GoToMainScreenPressed : LoginUiEvent()
}