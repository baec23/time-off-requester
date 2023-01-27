package com.gausslab.timeoffrequester.ui.screen.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gausslab.timeoffrequester.R
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.service.SheetsService
import com.gausslab.timeoffrequester.ui.screen.main.navigateToMainScreen
import com.gausslab.timeoffrequester.util.STRING.SAVED_USERID
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val googleSignInClient: GoogleSignInClient,
    private val sheetsService: SheetsService,
    private val dataStoreRepository: DataStoreRepository,
    private val navController: NavHostController
) : ViewModel() {

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.SignedIn -> {
                sheetsService.signedInAccount = event.account

                val scopes = listOf(SheetsScopes.SPREADSHEETS)
                val credential = GoogleAccountCredential.usingOAuth2(event.context, scopes)
                credential.selectedAccount = event.account.account
                val jsonFactory = GsonFactory()
                val httpTransport = NetHttpTransport()
                val service = Sheets.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(event.context.resources.getString(R.string.app_name))
                    .build()
                sheetsService.sheetsService = service
                navController.navigateToMainScreen()

            }
        }
    }

    init {
        viewModelScope.launch {
            val savedUserId = dataStoreRepository.getString(SAVED_USERID)
            if (savedUserId != null) {
                userRepository.tryAutoLogin(savedUserId)
                navController.navigateToMainScreen()
            }
        }
    }
}

sealed class LoginUiEvent {
    data class SignedIn(val account: GoogleSignInAccount, val context: Context) : LoginUiEvent()

}