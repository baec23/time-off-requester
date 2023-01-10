package com.gausslab.timeoffrequester.di

import android.content.Context
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.gausslab.timeoffrequester.model.User
import com.gausslab.timeoffrequester.repository.TimeOffRequestRepository
import com.gausslab.timeoffrequester.repository.UserRepository
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
    fun provideUserRepository() = UserRepository()

    @Singleton
    @Provides
    fun provideTimeOffRequestRepository() = TimeOffRequestRepository()


    @Singleton
    @Provides
    fun provideNavController(@ApplicationContext context: Context) =
        NavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
        }
}