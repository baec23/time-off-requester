package com.gausslab.timeoffrequester.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val navController: NavHostController
) : ViewModel() {

    private val _currNavScreenRoute: MutableState<String?> = mutableStateOf(null)
    val currNavScreenRoute: State<String?> = _currNavScreenRoute
    fun onEvent(event: AppUiEvent) {
        when(event){
            is AppUiEvent.BottomNavBarButtonPressed -> navController.navigate(event.pressedItem.route)
        }
    }
    init {
        MainScope().launch {
            navController.currentBackStackEntryFlow.collect{
                _currNavScreenRoute.value = it.destination.route
            }
        }
    }
}

sealed class AppUiEvent {
    data class BottomNavBarButtonPressed(val pressedItem: BottomNavBarItem) : AppUiEvent()
}
