package com.gausslab.timeoffrequester.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.ui.comp.BottomNavBarItem
import com.gausslab.timeoffrequester.ui.screen.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val navController: NavHostController
) : ViewModel() {

    private val _currNavScreenRoute: MutableState<String?> = mutableStateOf(null)
    val currNavScreenRoute: State<String?> = _currNavScreenRoute
    fun onEvent(event: AppUiEvent) {
        when (event) {
            is AppUiEvent.BottomNavBarButtonPressed -> navController.navigate(event.pressedItem.route)
            AppUiEvent.LogoutPressed -> viewModelScope.launch {
                dataStoreRepository.remove("savedUserId")
                navController.navigateToLoginScreen()
            }
        }
    }

    init {
        MainScope().launch {
            navController.currentBackStackEntryFlow.collect {
                _currNavScreenRoute.value = it.destination.route
            }
        }
    }
}

sealed class AppUiEvent {
    data class BottomNavBarButtonPressed(val pressedItem: BottomNavBarItem) : AppUiEvent()
    object LogoutPressed : AppUiEvent()
}
