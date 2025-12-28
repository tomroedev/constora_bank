package com.example.constorabank.core.common.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Unit tests for Validation utility functions.
 *
 * These tests focus on:
 * - email format rules
 * - sign-in validation behaviour
 * - account-creation password strength rules
 *
 * The goal is to verify behavioural boundaries rather than regex internals.
 */
class ValidationTest {

    // isEmailValid()

    @Test
    fun isEmailValid_returnsTrue_forSimpleValidEmail() {
        assertThat(
            Validation.isEmailValid(
                email = "tester@test.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forLongValidEmail() {
        assertThat(
            Validation.isEmailValid(
                email = "tester1234567890tester1234567890tester1234567890@test1234567890.co.uk"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forShortValidEmail() {
        assertThat(
            Validation.isEmailValid(
                email = "t@t.co"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forPlusTagEmail() {
        assertThat(
            Validation.isEmailValid(
                email = "test+tag@example.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forEmailWithUnderscore() {
        assertThat(
            Validation.isEmailValid(
                email = "first_last@example.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forEmailWithDotsInLocalPart() {
        assertThat(
            Validation.isEmailValid(
                email = "first.last@example.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forHyphenatedDomain() {
        assertThat(
            Validation.isEmailValid(
                email = "user@mail-server.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsTrue_forMultiLevelDomain() {
        assertThat(
            Validation.isEmailValid(
                email = "user@sub.domain.example.com"
            )
        ).isTrue()
    }

    @Test
    fun isEmailValid_returnsFalse_forEmptyEmail() {
        assertThat(
            Validation.isEmailValid(
                email = "" // empty string
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forMissingDomain() {
        assertThat(
            Validation.isEmailValid(
                email = "tester@" // no domain after @
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forMissingLocalPart() {
        assertThat(
            Validation.isEmailValid(
                email = "@tester.com" // no local part before @
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forMissingAtSymbol() {
        assertThat(
            Validation.isEmailValid(
                email = "testertester.com" // missing @ symbol
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forDomainWithoutTldDot() {
        assertThat(
            Validation.isEmailValid(
                email = "user@examplecom" // missing dot before TLD
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forSingleCharacterTld() {
        assertThat(
            Validation.isEmailValid(
                email = "user@example.c" // TLD too short (< 2 chars)
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forNumericTld() {
        assertThat(
            Validation.isEmailValid(
                email = "user@example.123" // TLD contains digits
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forEmailContainingSpaces() {
        assertThat(
            Validation.isEmailValid(
                email = "first last@example.com" // space in local part
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forLocalPartEndingWithDot() {
        assertThat(
            Validation.isEmailValid(
                email = "user.@example.com" // local part ends with dot
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forDomainStartingWithDot() {
        assertThat(
            Validation.isEmailValid(
                email = "user@.example.com" // domain begins with dot
            )
        ).isFalse()
    }

    @Test
    fun isEmailValid_returnsFalse_forEmailWithTrailingWhitespace() {
        assertThat(
            Validation.isEmailValid(
                email = "user@example.com " // trailing whitespace
            )
        ).isFalse()
    }

    // areSignInDetailsValid()

    @Test
    fun areSignInDetailsValid_returnsTrue_forSimpleValidEmailAndPassword() {
        assertThat(
            Validation.areSignInDetailsValid(
                email = "tester@test.com",
                password = "password"
            )
        ).isTrue()
    }

    @Test
    fun areSignInDetailsValid_returnsFalse_forInvalidEmailAndNonBlankPassword() {
        assertThat(
            Validation.areSignInDetailsValid(
                email = "testertester.com", // missing @
                password = "password"
            )
        ).isFalse()
    }

    @Test
    fun areSignInDetailsValid_returnsFalse_forValidEmailAndBlankPassword() {
        assertThat(
            Validation.areSignInDetailsValid(
                email = "tester@test.com",
                password = "" // blank password
            )
        ).isFalse()
    }

    @Test
    fun areSignInDetailsValid_returnsFalse_forValidEmailAndWhitespacePassword() {
        assertThat(
            Validation.areSignInDetailsValid(
                email = "tester@test.com",
                password = "   " // whitespace password
            )
        ).isFalse()
    }

    @Test
    fun areSignInDetailsValid_returnsFalse_forInvalidEmailAndBlankPassword() {
        assertThat(
            Validation.areSignInDetailsValid(
                email = "invalid-email", // invalid email
                password = "" // blank password
            )
        ).isFalse()
    }

    // areCreateAccountCredentialsValid()

    @Test
    fun areCreateAccountCredentialsValid_returnsTrue_forValidEmailAndStrongPassword() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "Password1!" // upper + lower + digit + special + >=8 chars
            )
        ).isTrue()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forValidEmailAndShortPassword() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "Ps1!" // < 8 chars
            )
        ).isFalse()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forPasswordMissingUppercase() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "password1!" // no uppercase
            )
        ).isFalse()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forPasswordMissingLowercase() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "PASSWORD1!" // no lowercase
            )
        ).isFalse()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forPasswordMissingDigit() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "Password!" // no digit
            )
        ).isFalse()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forPasswordMissingSpecialCharacter() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "tester@test.com",
                password = "Password1" // no special char
            )
        ).isFalse()
    }

    @Test
    fun areCreateAccountCredentialsValid_returnsFalse_forInvalidEmailEvenWithStrongPassword() {
        assertThat(
            Validation.areCreateAccountCredentialsValid(
                email = "invalid-email", // invalid email
                password = "Password1!"
            )
        ).isFalse()
    }

}