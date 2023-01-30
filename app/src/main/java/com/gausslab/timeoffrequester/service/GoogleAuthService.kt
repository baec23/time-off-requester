package com.gausslab.timeoffrequester.service

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ActivityScoped
class GoogleAuthService @Inject constructor() {
    val signedInAccount = MutableStateFlow<GoogleSignInAccount?>(null)
    fun setSignedInAccount(account: GoogleSignInAccount) {
        signedInAccount.value = account
    }
}