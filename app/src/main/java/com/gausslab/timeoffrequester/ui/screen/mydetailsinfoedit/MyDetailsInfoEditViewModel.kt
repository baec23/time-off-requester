package com.gausslab.timeoffrequester.ui.screen.mydetailsinfoedit

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDetailsInfoEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel(){
    val currUser = userRepository.currUser!!

    fun onEvent(event: MyDetailsInfoEditUiEvent){
        when(event){

            else -> {}
        }
    }
}

sealed class MyDetailsInfoEditUiEvent{

}