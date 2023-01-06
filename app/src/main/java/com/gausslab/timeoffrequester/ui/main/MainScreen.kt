package com.gausslab.timeoffrequester.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField

const val mainScreenRoute = "main_screen_route"

fun NavGraphBuilder.mainScreen() {
    composable(route = mainScreenRoute) {
        MainScreen()
    }
}

fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(route = mainScreenRoute, navOptions = navOptions)
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val startDateState by viewModel.startDateState
    val startDateInputFieldHasError by viewModel.startDateInputFieldHasError
    val startDateInputFieldErrorMessage by viewModel.startDateInputFieldErrorMessage

    val startTimeState by viewModel.startTimeState
    val startTimeInputFieldHasError by viewModel.startTimeInputFieldHasError
    val startTimeInputFieldErrorMessage by viewModel.startTimeInputFieldErrorMessage

    val endDateState by viewModel.endDateState
    val endDateInputFieldHasError by viewModel.endDateInputFieldHasError
    val endDateInputFieldErrorMessage by viewModel.endDateInputFieldErrorMessage

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            RemainingTimeOffRequestsBox()
            TimeOffRequestForm(
                startDate = startDateState,
                startDateInputFieldHasError = startDateInputFieldHasError,
                startDateInputFieldErrorMessage = startDateInputFieldErrorMessage,
                startTime = startTimeState,
                startTimeInputFieldHasError = startTimeInputFieldHasError,
                startTimeInputFieldErrorMessage = startTimeInputFieldErrorMessage,
                endDate = endDateState,
                endDateInputFieldHasError = endDateInputFieldHasError,
                endDateInputFieldErrorMessage = endDateInputFieldErrorMessage,
                onUiEvent = { viewModel.onEvent(it) }
            )
        }

//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)) {
//            Button(onClick = { viewModel.onEvent(MainUiEvent.GoToLoginScreenPressed) }) {
//                Text("Go to Login Screen")
//            }
//        }
    }
}

@Composable
fun RemainingTimeOffRequestsBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.DarkGray, shape = RectangleShape),
    ) {
        Text(modifier = Modifier.padding(5.dp), text = "남은 연차 수 : ${"asdf"}")
    }
}

@Composable
fun TimeOffRequestForm(
    modifier: Modifier = Modifier,
    startDate: String,
    startDateInputFieldHasError: Boolean,
    startDateInputFieldErrorMessage: String?,
    startTime: String,
    startTimeInputFieldHasError: Boolean,
    startTimeInputFieldErrorMessage: String?,
    endDate: String,
    endDateInputFieldHasError: Boolean,
    endDateInputFieldErrorMessage: String?,
    onUiEvent: (MainUiEvent) -> Unit,
) {
    Surface(modifier = modifier) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(text = "시작날짜: ")
                InputField(
                    value = startDate,
                    onValueChange = {
                        onUiEvent(MainUiEvent.StartDateChanged(it))
                        if (it.length == 6) {
                            onUiEvent(MainUiEvent.StartDateInputFieldHasError(false))
                        }
                        if (startDateInputFieldHasError) {
                            onUiEvent(MainUiEvent.StartDateInputFieldErrorMessage)
                        }
                    },
                    hasError = startDateInputFieldHasError,
                    errorMessage = startDateInputFieldErrorMessage,
                    placeholder = "예시> 220101",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Text(text = "시작시간: ")
                InputField(
                    modifier.fillMaxWidth(),
                    value = startTime,
                    onValueChange = {
                        onUiEvent(MainUiEvent.StartTimeChanged(it))
                        if (it.length == 5) {
                            onUiEvent(MainUiEvent.StartTimeInputFieldHasError(false))
                        }
                        if (startTimeInputFieldHasError) {
                            onUiEvent(MainUiEvent.StartTimeInputFieldErrorMessage)
                        }
                    },
                    hasError = startTimeInputFieldHasError,
                    errorMessage = startTimeInputFieldErrorMessage,
                    placeholder = "예시> 0900",
                    singleLine = true,
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Row() {
                Text(text = "종료날짜: ")
                Spacer(modifier = Modifier.width(3.dp))
                InputField(
                    value = endDate,
                    onValueChange = {
                        onUiEvent(MainUiEvent.EndDateChanged(it))
                        if (it.length == 6) {
                            onUiEvent(MainUiEvent.EndDateInputFieldHasError(false))
                        }
                        if (endDateInputFieldHasError) {
                            onUiEvent(MainUiEvent.EndDateInputFieldErrorMessage)
                        }
                    },
                    hasError = endDateInputFieldHasError,
                    errorMessage = endDateInputFieldErrorMessage,
                    placeholder = "예시> 221231",
                    singleLine = true,
                )
//                InputField(
//                    value = ,
//                    onValueChange =
//                )
            }
        }
    }
}
