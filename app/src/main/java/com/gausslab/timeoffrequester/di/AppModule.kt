package com.gausslab.timeoffrequester.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.gausslab.timeoffrequester.navigation.NavService
import com.gausslab.timeoffrequester.repository.DataStoreRepository
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepositoryImpl
import com.gausslab.timeoffrequester.repository.UserRepositoryImpl
import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.repository.datainterface.UserRepository
import com.gausslab.timeoffrequester.service.SheetsService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.sheets.v4.SheetsScopes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

    @Singleton
    @Provides
    fun provideTimeOffRequestRepository(): TimeOffRequestRepository = TimeOffRequestRepositoryImpl()

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context) =
        DataStoreRepository(context)

    @Singleton
    @Provides
    fun provideNavController(@ApplicationContext context: Context) =
        NavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
        }
    @Singleton
    @Provides
    fun provideNavigationService(navController: NavHostController) = NavService(navController = navController)
    @Singleton
    @Provides
    fun provideSheetsService() = SheetsService()

    @Singleton
    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .requestId().requestProfile().requestScopes(Scope(SheetsScopes.SPREADSHEETS)).build()
        return GoogleSignIn.getClient(context, gso)
    }
}