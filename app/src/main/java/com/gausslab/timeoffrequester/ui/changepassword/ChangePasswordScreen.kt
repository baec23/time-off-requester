package com.gausslab.timeoffrequester.ui.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.section.DisplaySection
import com.gausslab.timeoffrequester.ui.myprofile.MyProfileViewModel

const val changePasswordScreenRoute = "changePassword_screen_route"

fun NavGraphBuilder.changePasswordScreen() {
    composable(route = changePasswordScreenRoute) {
        ChangePasswordScreen()
    }
}

fun NavController.navigateToChangePasswordScreen(navOptions: NavOptions? = null) {
    this.navigate(route = changePasswordScreenRoute, navOptions = navOptions)
}

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val formState by viewModel.formState
    val newPassword = formState.newPassword
    val newPasswordCheck = formState.newPasswordCheck
    val isFormValid by viewModel.isFormValid

    val newPasswordInputFieldHasError by viewModel.newPasswordInputFieldHasError
    val newPasswordInputFieldErrorMessage by viewModel.newPasswordInputFieldErrorMessage

    val newPasswordCheckInputFieldHasError by viewModel.newPasswordCheckInputFieldHasError
    val newPasswordCheckInputFieldErrorMessage by viewModel.newPasswordCheckInputFieldErrorMessage


    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp)) {
        DisplaySection(headerText = "비밀번호 변경") {
            Column() {
                InputField(
                    value = newPassword,
                    onValueChange ={viewModel.onEvent(ChangePasswordUiEvent.NewPasswordChanged(it))},
                    label = "새로운 비밀번호",
                    placeholder = "새로운 비밀번호를 입력",
                    visualTransformation = PasswordVisualTransformation(),
                    hasError = newPasswordInputFieldHasError,
                    errorMessage = newPasswordInputFieldErrorMessage
                )
                InputField(
                    value = newPasswordCheck ,
                    onValueChange ={viewModel.onEvent(ChangePasswordUiEvent.NewPasswordCheckChanged(it))},
                    label = "새로운 비밀번호 재입력",
                    placeholder = "새로운 비밀번호를 재입력",
                    visualTransformation = PasswordVisualTransformation(),
                    hasError = newPasswordCheckInputFieldHasError,
                    errorMessage = newPasswordCheckInputFieldErrorMessage
                )
                Button(
                    modifier = Modifier,
                    onClick = { viewModel.onEvent(ChangePasswordUiEvent.ChangePasswordButtonPressed)},
                    enabled = isFormValid
                ) {
                    Text(text = "저장")
                }
            }
        }
    }
}
