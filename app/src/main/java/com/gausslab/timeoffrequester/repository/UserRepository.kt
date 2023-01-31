package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.remote.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val torApi: TorApi
) {
    var currUser: User? = null
        private set

    suspend fun signIn(account: GoogleSignInAccount) {
        val response = torApi.signIn(account.email!!, account.serverAuthCode!!)
        if (response.isSuccessful) {
            currUser = response.body()
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        val response = torApi.getUserByEmail(email)
        return response.body()
    }
}