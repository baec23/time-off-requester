package com.gausslab.timeoffrequester.ui.screen.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.screen.requestform.navigateToRequestFormScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "LandingViewModel"

@HiltViewModel
class LandingViewModel @Inject constructor(
    val googleSignInClient: GoogleSignInClient,
    private val userRepository: UserRepository,
    private val navController: NavHostController,
) : ViewModel() {
    fun onEvent(event: LandingUiEvent) {
        when (event) {
            is LandingUiEvent.SignedIn -> {
                viewModelScope.launch {
                    userRepository.signIn(event.account)
                    navController.navigateToRequestFormScreen()
//                    navController.navigateToNewUserFormScreen()
                }
            }
        }
    }
}

sealed class LandingUiEvent {
    data class SignedIn(val account: GoogleSignInAccount) : LandingUiEvent()
}