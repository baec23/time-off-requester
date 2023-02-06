package com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baec23.ludwig.component.section.DisplaySection
import com.gausslab.timeoffrequester.ui.screen.DetailsScreen

const val myDetailsInfoEditScreenRoute = "my_details_info_edit_screen_route"

fun NavGraphBuilder.myDetailsInfoEditScreen() {
    composable(route = "$myDetailsInfoEditScreenRoute") {
        MyDetailsInfoEditScreen()
    }
}

fun NavController.navigateToMyDetailsInfoEditeScreen(navOptions: NavOptions? =null){
    this.navigate(route = myDetailsInfoEditScreenRoute, navOptions = navOptions)
}

@Composable
fun MyDetailsInfoEditScreen(
    viewModel: MyDetailsInfoEditViewModel = hiltViewModel()
) {
    val currUser = viewModel.currUser

    DetailsScreen {
        DisplaySection(headerText = "내 추가 정보 수정") {
        }
    }
}