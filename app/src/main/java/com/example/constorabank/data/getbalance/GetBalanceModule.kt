package com.example.constorabank.data.getbalance

import com.example.constorabank.domain.getbalance.GetBalanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GetBalanceModule {

    @Binds
    @Singleton
    abstract fun bindGetBalanceRepository(
        impl: SupabaseGetBalanceRepository
    ): GetBalanceRepository
}