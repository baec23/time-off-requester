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


        val accountManager = AccountManager.get(this)
        accountManager.addAccount("Test", "TestAuthTokenType", arrayOf(" ", " "), null, this, AccountManagerCallback {
            val result = it.result
            Log.d("Test", "addAccount: ${it.result}")
        }, Handler())

//        val myAccount = accountManager.accounts.first()
//        val options = Bundle()
//        accountManager.getAuthToken(myAccount, "tokenType", options, this, AccountManagerCallback {
//            val result = it.result
//            result.getString(AccountManager.KEY_AUTHTOKEN)
//
//        }, Handler())
    }


//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_SIGN_IN) {
//            if (resultCode == RESULT_OK) {
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//                    .addOnSuccessListener { account ->
//                        val scopes = listOf(SheetsScopes.SPREADSHEETS)
//                        val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
//                        credential.selectedAccount = account.account
//                        val jsonFactory = JacksonFactory.getDefaultInstance()
//                        // GoogleNetHttpTransport.newTrustedTransport()
//                        val httpTransport =  AndroidHttp.newCompatibleTransport()
//                        val service = Sheets.Builder(httpTransport, jsonFactory, credential)
//                            .setApplicationName(getString(R.string.app_name))
//                            .build()
//                        createSpreadsheet(service)
//                    }
//                    .addOnFailureListener { e ->
//                    }
//            }
//        }
//    }
//    private fun requestSignIn(context: Context) {
//        /*
//        GoogleSignIn.getLastSignedInAccount(context)?.also { account ->
//            Timber.d("account=${account.displayName}")
//        }
//         */
//        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            // .requestEmail()
//            // .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
//            .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
//            .build()
//        val client = GoogleSignIn.getClient(context, signInOptions)
//        startActivityForResult(client.signInIntent, REQUEST_SIGN_IN)
//    }
//    private fun createSpreadsheet(service: Sheets) {
//        var spreadsheet = Spreadsheet()
//            .setProperties(
//                SpreadsheetProperties()
//                    .setTitle("CreateNewSpreadsheet")
//            )
//        launch(Dispatchers.Default) {
//            spreadsheet = service.spreadsheets().create(spreadsheet).execute()
//            Timber.d("ID: ${spreadsheet.spreadsheetId}")
//        }
//    }
}