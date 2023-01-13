package com.gausslab.timeoffrequester.ui.editmyprofile

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gausslab.timeoffrequester.ui.requestdetails.RequestDetailsViewModel

const val editMyProfileScreenRoute = "editMyProfile_screen_route"

fun NavGraphBuilder.editMyProfileScreen() {
    composable(
        route = "$editMyProfileScreenRoute/{userId}", arguments = listOf(
            navArgument("userId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val userId = it.arguments?.getString("userId")
        userId?.let {
            val viewModel: EditMyProfileViewModel = hiltViewModel()
            viewModel.setCurrUser(userId = userId)
            EditMyProfileScreen(
                viewModel = viewModel
            )
        }
    }
}

fun NavController.navigateToEditMyProfileScreen(userId: String, navOptions: NavOptions? = null) {
    val routeWithArguments = "$editMyProfileScreenRoute/$userId"
    this.navigate(route = routeWithArguments, navOptions = navOptions)
}

@Composable
fun EditMyProfileScreen(
    viewModel: EditMyProfileViewModel = hiltViewModel(),
) {
    Surface() {

    }
}