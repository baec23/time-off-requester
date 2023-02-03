package com.gausslab.timeoffrequester.ui.screen.landing

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignIn

const val landingScreenRoute = "landing_screen_route"
fun NavGraphBuilder.landingScreen() {
    composable(route = landingScreenRoute) {
        LandingScreen()
    }
}

fun NavController.navigateToLandingScreen(navOptions: NavOptions? = null) {
    navigate(route = landingScreenRoute, navOptions = navOptions)
}

//@Composable
//fun LandingScreen(
//    viewModel: LandingViewModel = hiltViewModel()
//) {
//    val startForResult =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val intent = result.data
//                if (result.data != null) {
//                    val account =
//                        GoogleSignIn.getSignedInAccountFromIntent(intent).result
//                    viewModel.onEvent(LandingUiEvent.SignedIn(account))
//                }
//            } else {
//                Log.d("GoogleLogin", "Something went wrong: ResultCode = ${result.resultCode}")
//            }
//        }
//    LaunchedEffect(true) {
//        startForResult.launch(viewModel.googleSignInClient.signInIntent)
//    }
//}

@Composable
fun LandingScreen(
    viewModel: LandingViewModel = hiltViewModel()
) {
    val silentSignIn =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val account =
                        GoogleSignIn.getSignedInAccountFromIntent(intent).result
                    viewModel.onEvent(LandingUiEvent.SilentlySignedIn(account))
                }
            } else {
                Log.d("GoogleLogin", "Something went wrong: ResultCode = ${result.resultCode}")
            }
        }

    val authCodeSignIn =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val account =
                        GoogleSignIn.getSignedInAccountFromIntent(intent).result
                    viewModel.onEvent(LandingUiEvent.SignedInWithAuthCode(account))
                }
            } else {
                Log.d("GoogleLogin", "Something went wrong: ResultCode = ${result.resultCode}")
            }
        }

    val silentlySignedIn by viewModel.silentSignInSuccessful.collectAsState()

    LaunchedEffect(silentlySignedIn){
        if(silentlySignedIn == null){
            silentSignIn.launch(viewModel.googleSignInClient.signInIntent)
        }else if(!silentlySignedIn!!){
            authCodeSignIn.launch(viewModel.authCodeGoogleSignInClient.signInIntent)
        }
    }
}