package com.gausslab.timeoffrequester.service

import android.content.Context
import android.util.Log
import com.gausslab.timeoffrequester.R
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayOutputStream
import java.util.Properties
import javax.inject.Inject
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

const val toEmail = "baec23@gmail.com"
@ActivityScoped
class GmailService @Inject constructor(
    private val context: Context,
    private val googleAuthService: GoogleAuthService
){
    private var gAccount: GoogleSignInAccount? = null
    private var gmailApi: Gmail? = null
    init{
        MainScope().launch{
            googleAuthService.signedInAccount.collect{
                if(it == null)
                    return@collect
                val scopes = listOf(GmailScopes.GMAIL_SEND)
                val credential = GoogleAccountCredential.usingOAuth2(context, scopes)
                credential.selectedAccount = it.account
                val jsonFactory = GsonFactory()
                val httpTransport = NetHttpTransport()
                gmailApi = Gmail.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(context.resources.getString(R.string.app_name))
                    .build()
                gAccount = it
            }
        }
    }

    suspend fun sendEmail(recipientEmail: String, subjectText: String, bodyText: String) {
        if(gAccount == null || gmailApi == null)
            return

        withContext(Dispatchers.IO){
            //SETUP MIME MESSAGE
            val props = Properties()
            val session = Session.getDefaultInstance(props)
            val email = MimeMessage(session)
            email.setFrom(gAccount!!.email)
            email.addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
            email.subject = subjectText
            email.setText(bodyText)

            //WRAP INTO GMAIL MESSAGE
            val buffer = ByteArrayOutputStream()
            email.writeTo(buffer)
            val rawMessageBytes = buffer.toByteArray()
            val encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes)
            val message = com.google.api.services.gmail.model.Message()
            message.raw = encodedEmail

            //TRY SEND
            try{
                gmailApi!!.users().messages().send("me", message).execute()
            }catch(e: GoogleJsonResponseException){
                val error = e.details
                if(e.statusCode == 403){
                    Log.d("GMAIL", "sendEmail: unable to send message: $error")
                } else {
                    throw e
                }
            }
        }
    }
}