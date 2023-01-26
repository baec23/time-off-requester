package com.gausslab.timeoffrequester.service

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.services.sheets.v4.Sheets
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class SheetsService{
    var signedInAccount: GoogleSignInAccount? = null
    var sheetsService: Sheets? = null
}