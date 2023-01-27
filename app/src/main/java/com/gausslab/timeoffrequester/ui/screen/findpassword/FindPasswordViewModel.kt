package com.gausslab.timeoffrequester.ui.screen.findpassword

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.ui.screen.login.navigateToLoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val navController: NavHostController
) : ViewModel() {
    private val _formState: MutableState<FindPasswordFormState> = mutableStateOf(
        FindPasswordFormState()
    )
    val formState:State<FindPasswordFormState> = _formState
    private val _isFormValid: MutableState<Boolean> = mutableStateOf(false)
    val isFormValid: State<Boolean> = _isFormValid

    private fun checkIfFormIsValid(){
        val form by _formState
        var isValid = true
        if (form.email.isEmpty()||!form.email.contains("@")||!form.email.contains(".")){
            isValid = false
        }
        _isFormValid.value = isValid
    }



    fun onEvent(event: FindPasswordUiEvent){
        when(event){
            is FindPasswordUiEvent.EmailChanged -> {
                _formState.value = _formState.value.copy(
                    email = event.email
                )
                checkIfFormIsValid()
            }
            FindPasswordUiEvent.SendButtonPressed -> {

                navController.navigateToLoginScreen()
            }
        }
    }
}
data class FindPasswordFormState(
    val email: String =""
)

sealed class FindPasswordUiEvent{
    data class EmailChanged(val email:String) : FindPasswordUiEvent()
    object SendButtonPressed : FindPasswordUiEvent()
}