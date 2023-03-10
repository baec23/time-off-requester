package com.gausslab.timeoffrequester.ui.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.service.SheetsService
import com.gausslab.timeoffrequester.ui.screen.findpassword.navigateToFindPasswordScreen
import com.gausslab.timeoffrequester.ui.screen.main.navigateToMainScreen
import com.gausslab.timeoffrequester.ui.screen.sheetstest.navigateToSheetsTestScreen
import com.gausslab.timeoffrequester.util.STRING.SAVED_USERID
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val googleSignInClient: GoogleSignInClient,
    private val sheetsService: SheetsService,
    private val dataStoreRepository: DataStoreRepository,
    private val navController: NavHostController
) : ViewModel() {

    private val _formState: MutableState<LoginFormState> = mutableStateOf(LoginFormState())
    val formState: State<LoginFormState> = _formState
    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid
    val autoLoginChecked: MutableState<Boolean> = mutableStateOf(false)

    private fun checkIfFormIsValid() {
        val form by _formState
        var isValid = true
        if (form.userEmail.isEmpty() || !form.userEmail.contains("@")) {
            isValid = false
        } else if (form.password.isEmpty() || form.password.length < 4) {
            isValid = false
        }
        _isFormValid.value = isValid
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.UserEmailChanged -> {
                _formState.value = _formState.value.copy(
                    userEmail = event.userEmail
                )
                checkIfFormIsValid()
            }

            is LoginUiEvent.PasswordChanged -> {
                _formState.value = _formState.value.copy(
                    password = event.password
                )
                checkIfFormIsValid()
            }

            LoginUiEvent.LoginButtonPressed -> {
                viewModelScope.launch {
                    val result = userRepository.tryLogin(
                        _formState.value.userEmail,
                        _formState.value.password
                    )

                    val myUser = result.getOrElse { exception ->
                        exception.message?.let {
                            it
                        }
                        _formState.value = _formState.value.copy(
                            userEmail = "",
                            password = ""
                        )
                        return@launch
                    }
                    if (autoLoginChecked.value) {
                        dataStoreRepository.putString(SAVED_USERID, myUser.id)
                    }
                    navController.navigateToMainScreen()
                }
            }

            LoginUiEvent.AutoLoginPressed -> {
                autoLoginChecked.value = !autoLoginChecked.value
            }

            LoginUiEvent.FindPasswordPressed -> {
                navController.navigateToFindPasswordScreen()
            }

            is LoginUiEvent.SignedIn -> {
                sheetsService.setSignedInAccount(event.account)
//                navController.navigateToMainScreen()
                navController.navigateToSheetsTestScreen()
            }
        }
    }

    init {
        viewModelScope.launch {
            val savedUserId = dataStoreRepository.getString(SAVED_USERID)
            if (savedUserId != null) {
                userRepository.tryAutoLogin(savedUserId)
                navController.navigateToSheetsTestScreen()
            }
        }
    }
}

data class LoginFormState(
    val userEmail: String = "",
    val password: String = ""
)

sealed class LoginUiEvent {
    data class UserEmailChanged(val userEmail: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object LoginButtonPressed : LoginUiEvent()
    object AutoLoginPressed : LoginUiEvent()
    object FindPasswordPressed : LoginUiEvent()


    data class SignedIn(val account: GoogleSignInAccount) : LoginUiEvent()

}