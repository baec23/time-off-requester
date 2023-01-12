package com.gausslab.timeoffrequester.ui.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.ui.main.navigateToMainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
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

            LoginUiEvent.LoginButtonPressed ->{
                viewModelScope.launch {
                    val result = userRepository.tryLogin(
                        _formState.value.userEmail,
                        _formState.value.password
                    )

                    val myUser = result.getOrElse { exception ->
                        exception.message?.let {
                            //
                        }
                        _formState.value = _formState.value.copy(
                            userEmail = "",
                            password = ""
                        )
                        return@launch
                    }
                    Log.d("asdfasdfasdf", "onEvent: ${_formState.value.userEmail} ${_formState.value.password} $result")
                    if (autoLoginChecked.value){
                        dataStoreRepository.putString("savedUserId", myUser.id)
                    }
                    navController.navigateToMainScreen()
                }
            }
            LoginUiEvent.AutoLoginPressed -> {
                autoLoginChecked.value = !autoLoginChecked.value
            }
        }
    }

    init {
        viewModelScope.launch {
            val savedUserId = dataStoreRepository.getString("savedUserId")
            if(savedUserId != null){
                userRepository.tryAutoLogin(savedUserId)
                navController.navigateToMainScreen()
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

}