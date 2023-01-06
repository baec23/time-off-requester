package com.gausslab.timeoffrequester.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {
    private val _startDateState: MutableState<String> = mutableStateOf("")
    val startDateState: State<String> = _startDateState
    fun onEvent(event: MainUiEvent) {
        when(event){
            MainUiEvent.GoToLoginScreenPressed -> navController.navigateToLoginScreen()
            is MainUiEvent.StartDateChanged -> {
                _startDateState.value = event.startDate
            }
        }
    }
}

sealed class MainUiEvent {
    data class StartDateChanged(val startDate:String) : MainUiEvent()
    object GoToLoginScreenPressed : MainUiEvent()
}