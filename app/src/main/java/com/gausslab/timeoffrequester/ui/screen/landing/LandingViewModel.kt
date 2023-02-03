package com.gausslab.timeoffrequester.ui.screen.landing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.screen.requestform.navigateToRequestFormScreen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val TAG = "LandingViewModel"

@HiltViewModel
class LandingViewModel @Inject constructor(
    val googleSignInClient: GoogleSignInClient,
    @Named("authCode") val authCodeGoogleSignInClient: GoogleSignInClient,
    private val userRepository: UserRepository,
    private val navController: NavHostController,
) : ViewModel() {
    private val _silentSignInSuccessful = MutableStateFlow<Boolean?>(null)
    val silentSignInSuccessful = _silentSignInSuccessful.asStateFlow()

    private val _authCodeSignInSuccessful = MutableStateFlow<Boolean?>(null)
    val authCodeSignInSuccessful = _authCodeSignInSuccessful.asStateFlow()

    fun onEvent(event: LandingUiEvent) {
        when (event) {
            is LandingUiEvent.SilentlySignedIn -> {
                viewModelScope.launch {
                    _silentSignInSuccessful.value = userRepository.trySilentSignIn(event.account)
                    if(_silentSignInSuccessful.value!!){
                        Log.d(TAG, "onEvent: Silent Sign In Success")
                        navController.navigateToRequestFormScreen()
                    }
                }
            }

            is LandingUiEvent.SignedInWithAuthCode -> {
                viewModelScope.launch {
                    _authCodeSignInSuccessful.value = userRepository.trySignIn(event.account)
                    if(_authCodeSignInSuccessful.value!!){
                        Log.d(TAG, "onEvent: Auth Code Sign In Success")
                        navController.navigateToRequestFormScreen()
                    }
                }
            }

            LandingUiEvent.SilentSignInFailed -> _silentSignInSuccessful.value = false
        }
    }
}

sealed class LandingUiEvent {
    data class SilentlySignedIn(val account: GoogleSignInAccount) : LandingUiEvent()
    data class SignedInWithAuthCode(val account: GoogleSignInAccount) : LandingUiEvent()
    object SilentSignInFailed : LandingUiEvent()
}