package com.gausslab.timeoffrequester

import android.content.Context
import android.content.Intent
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnSuccessListener { account ->
                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
                        val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
                        credential.selectedAccount = account.account
                        val jsonFactory = JacksonFactory.getDefaultInstance()
                        // GoogleNetHttpTransport.newTrustedTransport()
                        val httpTransport =  AndroidHttp.newCompatibleTransport()
                        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
                            .setApplicationName(getString(R.string.app_name))
                            .build()
                        createSpreadsheet(service)
                    }
                    .addOnFailureListener { e ->
                    }
            }
        }
    }
    private fun requestSignIn(context: Context) {
        /*
        GoogleSignIn.getLastSignedInAccount(context)?.also { account ->
            Timber.d("account=${account.displayName}")
        }
         */
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // .requestEmail()
            // .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
            .build()
        val client = GoogleSignIn.getClient(context, signInOptions)
        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
    }
    private fun createSpreadsheet(service: Sheets) {
        var spreadsheet = Spreadsheet()
            .setProperties(
                SpreadsheetProperties()
                    .setTitle("CreateNewSpreadsheet")
            )
        launch(Dispatchers.Default) {
            spreadsheet = service.spreadsheets().create(spreadsheet).execute()
            Timber.d("ID: ${spreadsheet.spreadsheetId}")
        }
    }
}