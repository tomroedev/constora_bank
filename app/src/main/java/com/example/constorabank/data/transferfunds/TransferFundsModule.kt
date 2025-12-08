package com.example.constorabank.data.transferfunds

import com.example.constorabank.domain.transferfunds.TransferFundsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TransferFundsModule {

    @Binds
    @Singleton
    abstract fun bindTransferFundsRepository(
        impl: SupabaseTransferFundsRepository
    ): TransferFundsRepository
}