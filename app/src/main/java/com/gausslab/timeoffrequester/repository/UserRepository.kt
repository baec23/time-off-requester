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

    suspend fun trySilentSignIn(account: GoogleSignInAccount): Boolean {
        val response = torApi.signIn(account.email!!)
        return if (response.isSuccessful) {
            currUser = response.body()
            true
        } else false
    }

    suspend fun trySignIn(account: GoogleSignInAccount): Boolean {
        val response = torApi.signIn(account.email!!, account.serverAuthCode)
        return if (response.isSuccessful) {
            currUser = response.body()
            true
        } else false
    }

    suspend fun getUserByEmail(email: String): User? {
        val response = torApi.getUserByEmail(email)
        return response.body()
    }
}