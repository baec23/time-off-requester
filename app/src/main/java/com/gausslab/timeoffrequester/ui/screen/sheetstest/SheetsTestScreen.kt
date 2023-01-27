package com.gausslab.timeoffrequester.ui.screen.sheetstest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.baec23.ludwig.component.button.StatefulButton
import com.baec23.ludwig.component.section.DisplaySection

const val sheetsTestScreenRoute = "sheets_test_screen_route"
fun NavGraphBuilder.sheetsTestScreen() {
    composable(route = sheetsTestScreenRoute) {
        SheetsTestScreen()
    }
}

fun NavController.navigateToSheetsTestScreen(navOptions: NavOptions? = null) {
    navigate(route = sheetsTestScreenRoute, navOptions = navOptions)
}

@Composable
fun SheetsTestScreen(
    viewModel: SheetsTestViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DisplaySection(headerText = "Test Buttons") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatefulButton(text = "연차신청서") {
                    viewModel.onEvent(SheetsTestUiEvent.AddToFormPressed)
                }
                StatefulButton(text = "연차사용내역") {
                    viewModel.onEvent(SheetsTestUiEvent.AddToUsagePressed)
                }
                StatefulButton(text = "둘다") {
                    viewModel.onEvent(SheetsTestUiEvent.AddToBothPressed)
                }
            }
        }
    }
}
