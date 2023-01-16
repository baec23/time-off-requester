package com.gausslab.timeoffrequester.ui.findpassword

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.inputfield.InputValidator

const val findPasswordScreenRoute = "find_password_screen_route"


fun NavGraphBuilder.findPasswordScreen() {
    composable(route = findPasswordScreenRoute) {
        FindPasswordScreen()
    }
}

fun NavController.navigateToFindPasswordScreen(navOptions: NavOptions? = null) {
    this.navigate(route = findPasswordScreenRoute, navOptions = navOptions)
}

@Composable
fun FindPasswordScreen(
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val formState by viewModel.formState
    val email = formState.email
    val isFormValid by viewModel.isFormValid

//    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column() {
            Spacer(modifier = Modifier.height(120.dp))
            InputField(
                value = email,
                onValueChange = { viewModel.onEvent(FindPasswordUiEvent.EmailChanged(it)) },
                label = "Email",
                placeholder = "Email",
                inputValidator = InputValidator.Email
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
//                    fun Context.sendMail(to: String, subject: String){
//                        try {
//                            val intent = Intent(Intent.ACTION_SEND)
//                            intent.type = "mailto:"
//                            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
//                            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//                            startActivity(intent)
//                        }catch (e: AccessDeniedException){
//
//                        }catch (t: Throwable){
//
//                        }
//                    }
//                    context.sendMail(email, "12345678")
                    viewModel.onEvent(FindPasswordUiEvent.SendButtonPressed)
                },
                enabled = isFormValid
            ) {
                Text(text = "Send")
            }
        }
    }
}