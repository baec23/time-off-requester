package com.gausslab.timeoffrequester.ui.editmyprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.model.User
import com.gausslab.timeoffrequester.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMyProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val navController: NavHostController
) : ViewModel() {

    private val _currUser = MutableStateFlow(User())
    val currUser = _currUser

    fun setCurrUser(userId: String){
        viewModelScope.launch {
            _currUser.emit(
                userRepository.getUserById(userId = userId)
            )
        }
    }

    fun onEvent(event: EditMyProfileUiEvent){
//        when(event){
//
//        }
    }

}

sealed class EditMyProfileUiEvent{

}