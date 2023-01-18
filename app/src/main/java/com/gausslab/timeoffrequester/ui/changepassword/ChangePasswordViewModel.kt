package com.gausslab.timeoffrequester.ui.changepassword

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.datainterface.UserRepository
import com.gausslab.timeoffrequester.repository.UserRepositoryImpl
import com.gausslab.timeoffrequester.ui.myprofile.navigateToMyProfileScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    val newPasswordInputFieldHasError : MutableState<Boolean> = mutableStateOf(true)
    val newPasswordInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    val newPasswordCheckInputFieldHasError : MutableState<Boolean> = mutableStateOf(true)
    val newPasswordCheckInputFieldErrorMessage: MutableState<String?> = mutableStateOf("")

    private val _formState: MutableState<ChangePasswordFormState> =
        mutableStateOf(ChangePasswordFormState())
    val formState: State<ChangePasswordFormState> = _formState
    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid

    private fun checkIfFormIsValid() {
        val form by _formState
        var isValid = true
        if (form.newPassword.isEmpty() || form.newPassword.length < 4) {
            isValid = false
        } else if (form.newPasswordCheck.isEmpty() || form.newPasswordCheck.length < 4) {
            isValid = false
        } else if (form.newPassword != form.newPasswordCheck) {
            isValid = false
        }
        _isFormValid.value = isValid
    }

    private fun String.isValidPassword(): Boolean{
        return this.length>=4
    }

    fun onEvent(event: ChangePasswordUiEvent) {
        when (event) {
            is ChangePasswordUiEvent.NewPasswordChanged -> {
                _formState.value = _formState.value.copy(
                    newPassword = event.newPassword
                )
                if (event.newPassword.isValidPassword()){
                    newPasswordInputFieldHasError.value = false
                }else{
                    newPasswordInputFieldHasError.value = true
                    newPasswordInputFieldErrorMessage.value = "너무 짧음"
                }
            }

            is ChangePasswordUiEvent.NewPasswordCheckChanged -> {
                _formState.value = _formState.value.copy(
                    newPasswordCheck = event.newPasswordCheck
                )
                if (event.newPasswordCheck.isValidPassword()){
                    newPasswordCheckInputFieldHasError.value = false
                }else{
                    newPasswordCheckInputFieldHasError.value = true
                    newPasswordCheckInputFieldErrorMessage.value = "너무 짧음"
                }
            }
            ChangePasswordUiEvent.ChangePasswordButtonPressed -> {
                viewModelScope.launch {
                    val form by _formState
                    val user = userRepository.currUser!!.copy(password = form.newPassword)
                    userRepository.saveUser(user)
                }
                navController.navigateToMyProfileScreen()
            }
        }
        checkIfFormIsValid()
    }
}

data class ChangePasswordFormState(
    val newPassword: String = "",
    val newPasswordCheck: String = ""
)

sealed class ChangePasswordUiEvent {
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordUiEvent()
    data class NewPasswordCheckChanged(val newPasswordCheck: String) : ChangePasswordUiEvent()
    object ChangePasswordButtonPressed : ChangePasswordUiEvent()
}