package com.gausslab.timeoffrequester.ui.screen.myprofiledetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baec23.ludwig.component.section.DisplaySection

const val myProfileDetailsScreenRoute = "myProfileDetails_screen_route"

fun NavGraphBuilder.myProfileDetailsScreen() {
    composable(
        route = "$myProfileDetailsScreenRoute/{userId}", arguments = listOf(
            navArgument("userId") {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val userId = it.arguments?.getString("userId")
        userId?.let {
            val viewModel: MyProfileDetailsViewModel = hiltViewModel()
            viewModel.setCurrUser(userId = userId)
            MyProfileDetailsScreen(
                viewModel = viewModel
            )
        }
    }
}

fun NavController.navigateToMyProfileDetailsScreen(userId: String, navOptions: NavOptions? = null) {
    val routeWithArguments = "$myProfileDetailsScreenRoute/$userId"
    this.navigate(route = routeWithArguments, navOptions = navOptions)
}

@Composable
fun MyProfileDetailsScreen(
    viewModel: MyProfileDetailsViewModel = hiltViewModel(),
) {
    val currUser by viewModel.currUser.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        DisplaySection(headerText = "내 정보 확인") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                ShowTextField(
                    title = "이름",
                    content = currUser.username
                )
                ShowTextField(
                    title = "id",
                    content = currUser.id
                )
                ShowTextField(
                    title = "입사날짜",
                    content = currUser.startDate
                )
                ShowTextField(
                    title = "부서",
                    content = currUser.partName
                )
                ShowTextField(
                    title = "직급",
                    content = currUser.position
                )
            }
        }
    }
}

@Composable
fun ShowTextField(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Surface(
        modifier = modifier
    ) {
        Row() {
            Text(modifier = Modifier.weight(1f), text = title)
            Spacer(modifier = Modifier.width(10.dp))
            Text(modifier= Modifier.weight(3f),text = content)
        }
    }
}