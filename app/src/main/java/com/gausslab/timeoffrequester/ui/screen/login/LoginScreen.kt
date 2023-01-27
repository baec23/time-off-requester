package com.gausslab.timeoffrequester.ui.screen.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.inputfield.InputField
import com.baec23.ludwig.component.inputfield.InputValidator
import com.baec23.ludwig.component.section.DisplaySection
import com.google.android.gms.auth.api.signin.GoogleSignIn

const val loginScreenRoute = "login_screen_route"

fun NavGraphBuilder.loginScreen() {
    composable(route = loginScreenRoute) {
        LoginScreen()
    }
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(route = loginScreenRoute, navOptions = navOptions)
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val account =
                        GoogleSignIn.getSignedInAccountFromIntent(intent).result
                    viewModel.onEvent(LoginUiEvent.SignedIn(account, context))
                }
            }
        }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DisplaySection(headerText = "Login") {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    onClick = {
                        startForResult.launch(viewModel.googleSignInClient.signInIntent)
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
                }
            }
        }
    }
}