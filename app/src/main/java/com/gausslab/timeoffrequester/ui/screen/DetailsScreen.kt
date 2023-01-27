package com.gausslab.timeoffrequester.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.gausslab.timeoffrequester.navigation.NavService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    content: @Composable () -> Unit,
) {
    val viewModel: DetailsScreenViewModel = hiltViewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { DetailsTopBar(
            onBackPress = { viewModel.navService.navigateUp() },
            title = viewModel.navService.currNavScreenRoute.toString()
        )}
    ) {
        Column(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun DetailsTopBar(
    onBackPress: () -> Unit,
    title: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onBackPress) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        title?.let {
            Text(
                modifier = Modifier.weight(2f),
                text = it
            )
        }
    }
}


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    val navService: NavService
) : ViewModel()