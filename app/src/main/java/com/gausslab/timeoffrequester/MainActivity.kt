package com.gausslab.timeoffrequester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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