package com.gausslab.timeoffrequester.ui.screen.newuserform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.remote.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewUserFormViewModel @Inject constructor(
    private val torApi: TorApi
) : ViewModel() {
    private val _userForm = MutableStateFlow(UserForm())
    val userForm = _userForm.asStateFlow()

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()
    fun onEvent(event: NewUserFormUiEvent) {
        when (event) {
            is NewUserFormUiEvent.EmailChanged -> _userForm.value = _userForm.value.copy(email = event.email)
            is NewUserFormUiEvent.PositionChanged -> _userForm.value = _userForm.value.copy(position = event.position)
            is NewUserFormUiEvent.TeamChanged -> _userForm.value = _userForm.value.copy(team = event.team)
            is NewUserFormUiEvent.DisplayNameChanged -> _userForm.value = _userForm.value.copy(displayName = event.displayName)
            is NewUserFormUiEvent.HiredDateChanged -> _userForm.value = _userForm.value.copy(hiredDate = event.hiredDate)
            is NewUserFormUiEvent.BirthdayChanged -> _userForm.value = _userForm.value.copy(birthday = event.birthday)
            NewUserFormUiEvent.SubmitPressed -> {
                _isBusy.value = true
                viewModelScope.launch {
                    torApi.saveUser(_userForm.value.toUser())
                    delay(2000)
                    _isBusy.value = false
                }
            }
        }
    }
}

sealed class NewUserFormUiEvent {
    data class EmailChanged(val email:String) : NewUserFormUiEvent()
    data class PositionChanged(val position:String) : NewUserFormUiEvent()
    data class TeamChanged(val team:String) : NewUserFormUiEvent()
    data class DisplayNameChanged(val displayName:String) : NewUserFormUiEvent()
    data class HiredDateChanged(val hiredDate:LocalDate) : NewUserFormUiEvent()
    data class BirthdayChanged(val birthday:LocalDate) : NewUserFormUiEvent()
    object SubmitPressed : NewUserFormUiEvent()
}

data class UserForm(
    val email: String? = null,
    val position: String? = null,
    val team: String? = null,
    val displayName: String? = null,
    val hiredDate: LocalDate? = null,
    val birthday: LocalDate? = null
){
    fun toUser(): User {
        if(email == null || position == null || team == null || displayName == null || hiredDate == null || birthday == null)
            throw Exception()
        return User(email, position, team, displayName, hiredDate, birthday)
    }
}