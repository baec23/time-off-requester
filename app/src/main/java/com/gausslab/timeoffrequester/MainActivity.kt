package com.gausslab.timeoffrequester

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gausslab.timeoffrequester.ui.App
import com.gausslab.timeoffrequester.ui.theme.TimeOffRequesterTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Firebase.firestore.clearPersistence()
        super.onCreate(savedInstanceState)
        setContent {
            TimeOffRequesterTheme {
                App()
            }
        }
    }
}