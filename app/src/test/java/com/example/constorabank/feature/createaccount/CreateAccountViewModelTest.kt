package com.example.constorabank.feature.createaccount

import app.cash.turbine.test
import com.example.constorabank.MainDispatcherRule
import com.example.constorabank.data.auth.FakeAuthRepository
import com.example.constorabank.domain.auth.CreateAccountUseCase
import com.example.constorabank.domain.auth.RegistrationError
import com.example.constorabank.domain.auth.RegistrationResult
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateAccountViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial state is an empty email and loading set to false`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.success()
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewmodel = CreateAccountViewModel(useCase)

        // When
        // nothing more is done, i.e. we've just entered the screen

        // Then
        assertThat(viewmodel.email.value).isEqualTo("")
        assertThat(viewmodel.isLoading.value).isFalse()
    }

    @Test
    fun `onEmailChanged updates email state`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.success()
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewModel = CreateAccountViewModel(useCase)

        // Check email is empty before setting it to the updated value
        assertThat(viewModel.email.value).isEqualTo("")

        // When
        viewModel.onEmailChanged("tester@test.com")

        // Then
        assertThat(viewModel.email.value).isEqualTo("tester@test.com")
    }

    /**
     * Uses Turbine to test the isLoading StateFlow deterministically.
     * Turbine suspends at each awaitItem() and fails if an expected emission is missing,
     * out of order, or the flow completes early. This removes race conditions from
     * manual collection under runTest and makes the expected loading transitions
     * [false -> true -> false] explicit and verifiable.
     */
    @Test
    fun `createAccount request updates loading from false, to true, then back to false`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.success()
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewModel = CreateAccountViewModel(useCase)

        viewModel.isLoading.test {
            // Initial value from StateFlow
            assertThat(awaitItem()).isFalse()

            // Trigger account creation
            viewModel.createAccount("tester@test.com", "Password123!")

            // True whilst the work is on-going
            assertThat(awaitItem()).isTrue()

            // False when the work has finished
            assertThat(awaitItem()).isFalse()

            // Clean up
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `createAccount success updates loading and emits success`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.success()
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewModel = CreateAccountViewModel(useCase)

        // Start listening before we trigger the action
        val resultDeferred = async { viewModel.registrationResult.first() }

        // When
        viewModel.createAccount(" tester@test.com ", "Password123!")
        advanceUntilIdle() // let viewModelScope coroutines run

        // Then (loading finished)
        assertThat(viewModel.isLoading.value).isFalse()

        // Use case should have called repository with trimmed email
        assertThat(fakeRepo.lastEmail).isEqualTo("tester@test.com")
        assertThat(fakeRepo.lastPassword).isEqualTo("Password123!")

        // Emitted result is Success
        val result = resultDeferred.await()
        assertThat(result).isInstanceOf(RegistrationResult.Success::class.java)
    }

    @Test
    fun `createAccount failure emits failure and stops loading`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.failure(RegistrationError.CREDENTIAL_CONFLICT)
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewModel = CreateAccountViewModel(useCase)
        val resultDeferred = async { viewModel.registrationResult.first() }

        // When
        viewModel.createAccount("tester@test.com", "Password123!")
        advanceUntilIdle()

        // Then
        assertThat(viewModel.isLoading.value).isFalse()

        val result = resultDeferred.await()
        assertThat(result).isInstanceOf(RegistrationResult.Failure::class.java)

        val failure = result as RegistrationResult.Failure
        assertThat(failure.error).isEqualTo(RegistrationError.CREDENTIAL_CONFLICT)
    }

    @Test
    fun `createAccount exception is mapped to UNKNOWN failure`() = runTest {
        // Given
        val fakeRepo = FakeAuthRepository.throws()
        val useCase = CreateAccountUseCase(fakeRepo, mainDispatcherRule.testDispatcher)
        val viewModel = CreateAccountViewModel(useCase)
        val resultDeferred = async { viewModel.registrationResult.first() }

        // When
        viewModel.createAccount("tester@test.com", "Password123!")
        advanceUntilIdle()

        // Then
        assertThat(viewModel.isLoading.value).isFalse()

        val result = resultDeferred.await()
        assertThat(result).isInstanceOf(RegistrationResult.Failure::class.java)

        val failure = result as RegistrationResult.Failure
        assertThat(failure.error).isEqualTo(RegistrationError.UNKNOWN)
    }
}