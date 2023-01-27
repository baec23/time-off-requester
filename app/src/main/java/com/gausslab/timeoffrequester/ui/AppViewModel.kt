package com.gausslab.timeoffrequester.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.navigation.NavService
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val navService: NavService,
) : ViewModel() {
}
