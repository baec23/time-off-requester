package com.gausslab.timeoffrequester.ui.screen.newuserform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.ButtonState
import com.baec23.ludwig.component.button.LabelledValueButton
import com.baec23.ludwig.component.button.StatefulButton
import com.baec23.ludwig.component.datepicker.DatePicker
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.inputfield.InputValidator
import com.baec23.ludwig.component.section.DisplaySection

const val newUserFormScreenRoute = "new_user_form_screen_route"
fun NavGraphBuilder.newUserFormScreen() {
    composable(route = newUserFormScreenRoute) {
        NewUserFormScreen()
    }
}

fun NavController.navigateToNewUserFormScreen(navOptions: NavOptions? = null) {
    navigate(route = newUserFormScreenRoute, navOptions = navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewUserFormScreen(
    viewModel: NewUserFormViewModel = hiltViewModel()
) {
    val userFormState by viewModel.userForm.collectAsState()
    val isBusy by viewModel.isBusy.collectAsState()

    DisplaySection(headerText = "사용자 등록") {
        InputField(
            value = userFormState.email ?: "",
            onValueChange = { viewModel.onEvent(NewUserFormUiEvent.EmailChanged(it)) },
            label = "회사 이메일",
            maxLines = 1,
            inputValidator = InputValidator.Email
        )
        InputField(
            value = userFormState.displayName ?: "",
            onValueChange = { viewModel.onEvent(NewUserFormUiEvent.DisplayNameChanged(it)) },
            label = "이름",
            maxLines = 1,
            inputValidator = InputValidator.TextNoSpaces
        )
        InputField(
            value = userFormState.position ?: "",
            onValueChange = { viewModel.onEvent(NewUserFormUiEvent.PositionChanged(it)) },
            label = "직위",
            maxLines = 1,
            inputValidator = InputValidator.TextWithSpaces
        )
        InputField(
            value = userFormState.team ?: "",
            onValueChange = { viewModel.onEvent(NewUserFormUiEvent.TeamChanged(it)) },
            label = "부서",
            maxLines = 1,
            inputValidator = InputValidator.TextWithSpaces
        )
        var hiredDatePickerShowing by remember { mutableStateOf(false) }
        LabelledValueButton(label = "입사 일자", value = userFormState.hiredDate.toString()) {
            hiredDatePickerShowing = true
        }
        AnimatedVisibility(visible = hiredDatePickerShowing) {
            AlertDialog(onDismissRequest = { hiredDatePickerShowing = false }) {
                DatePicker(
                    onCancelled = { hiredDatePickerShowing = false },
                    onDateSelectionFinalized = {
                        viewModel.onEvent(NewUserFormUiEvent.HiredDateChanged(it))
                        hiredDatePickerShowing = false
                    })
            }
        }

        var birthdayDatePickerShowing by remember { mutableStateOf(false) }
        LabelledValueButton(label = "생일", value = userFormState.birthday.toString()) {
            birthdayDatePickerShowing = true
        }
        AnimatedVisibility(visible = birthdayDatePickerShowing) {
            AlertDialog(onDismissRequest = { birthdayDatePickerShowing = false }) {
                DatePicker(
                    onCancelled = { birthdayDatePickerShowing = false },
                    onDateSelectionFinalized = {
                        viewModel.onEvent(NewUserFormUiEvent.BirthdayChanged(it))
                        birthdayDatePickerShowing = false
                    })
            }
        }
        StatefulButton(text = "저장", state = if(isBusy) ButtonState.Loading else ButtonState.Enabled) {
            viewModel.onEvent(NewUserFormUiEvent.SubmitPressed)
        }
    }
}