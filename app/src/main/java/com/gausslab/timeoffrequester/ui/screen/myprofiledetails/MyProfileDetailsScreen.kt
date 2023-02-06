package com.gausslab.timeoffrequester.ui.screen.myprofiledetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.gausslab.timeoffrequester.ui.screen.DetailsScreen


const val myProfileDetailsScreenRoute = "myProfileDetails_screen_route"

fun NavGraphBuilder.myProfileDetailsScreen() {
    composable(route = myProfileDetailsScreenRoute) {
        MyProfileDetailsScreen()
    }
}

fun NavController.navigateToMyProfileDetailsScreen(navOptions: NavOptions? = null) {
    this.navigate(route = myProfileDetailsScreenRoute, navOptions = navOptions)
}

@Composable
fun MyProfileDetailsScreen(
    viewModel: MyProfileDetailsViewModel = hiltViewModel(),
) {
    val currUser = viewModel.currUser
    DetailsScreen {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DisplaySection(headerText = "내 정보 확인") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ShowTextField(
                        title = "이름",
                        content = currUser.displayName
                    )
                    ShowTextField(
                        title = "id",
                        content = currUser.email
                    )
                    ShowTextField(
                        title = "입사날짜",
                        content = currUser.hiredDate.toString()
                    )
                    ShowTextField(
                        title = "부서",
                        content = currUser.team
                    )
                    ShowTextField(
                        title = "직급",
                        content = currUser.position
                    )
                }
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
            Text(modifier = Modifier.weight(3f), text = content)
        }
    }
}