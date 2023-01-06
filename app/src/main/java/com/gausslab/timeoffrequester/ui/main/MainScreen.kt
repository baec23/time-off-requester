package com.gausslab.timeoffrequester.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

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

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            RemainingTimeOffRequestsBox()
            TimeOffRequestForm()
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

    ) {
    Surface(modifier = modifier) {
        Column {
            Row() {
                Text(text = "시작날짜")
                //캘린더

            }
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun DatePicker(
//    value: String,
//    onValueChange: (String) -> Unit = {},
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    pattern: String = "yyyy-MM-dd",
//) {
//    val formatter = DateTimeFormatter.ofPattern(pattern)
//    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
//    val dialog = DatePickerDialog(
//        LocalContext.current,
//        { _, year, month, dayOfMonth ->
//            onValueChange(LocalDate.of(year, month + 1, dayOfMonth).toString())
//
//            Log.d("asdfasdfadsfadsfasdf", "DatePicker: " + LocalDate.of(year, month + 1, dayOfMonth).toString())
//        },
//        date.year,
//        date.monthValue - 1,
//        date.dayOfMonth,
//    )
//
//    TextField(
//        modifier = Modifier.clickable{dialog.show()},
//        value = date.toString(),
//        onValueChange = {},
//        enabled = false,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//    )
//}