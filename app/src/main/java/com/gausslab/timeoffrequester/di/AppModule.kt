package com.gausslab.timeoffrequester.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.gausslab.timeoffrequester.remote.api.TorApi
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepositoryImpl
import com.gausslab.timeoffrequester.repository.UserRepository
import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

const val baseUrl = "http://10.0.2.2:8080/tor-api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideTimeOffRequestRepository(): TimeOffRequestRepository = TimeOffRequestRepositoryImpl()

    @Singleton
    @Provides
    fun provideNavController(@ApplicationContext context: Context) =
        NavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
        }

    @Singleton
    @Provides
    fun provideUserRepository(torApi: TorApi) = UserRepository(torApi)

    @Singleton
    @Provides
    fun provideTorApi(): TorApi {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context -> LocalDate.parse(json.asString) }
            )
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer<LocalDate?> { localDate, typeOfT, context -> JsonPrimitive(localDate.toString()) }
            )
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context -> LocalDateTime.parse(json.asString) }
            )
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer<LocalDateTime?> { localDateTime, typeOfT, context ->
                    JsonPrimitive(
                        localDateTime.toString()
                    )
                }
            )
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TorApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .requestProfile()
            .requestServerAuthCode("176428785805-3gmlp03f2sjjovk3apqeihbukcnacck7.apps.googleusercontent.com")
            .requestScopes(
                Scope(SheetsScopes.SPREADSHEETS),
                Scope(GmailScopes.GMAIL_SEND),
                Scope(CalendarScopes.CALENDAR)
            )
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}