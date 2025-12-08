package com.example.constorabank.core.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Reasons for using this instead of calling Dispatchers.IO directly:
 *
 * Testability  – We can swap Dispatchers.IO with a test dispatcher in unit tests.
 * Decoupling   – Classes don't hardcode Dispatchers.IO; they receive it via constructor injection.
 * Consistency  – Centralises dispatcher configuration in one place.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}