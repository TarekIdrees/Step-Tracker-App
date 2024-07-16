package com.tareq.auth.domain

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasUppercaseCharacter: Boolean = false,
    val hasLowercaseCharacter: Boolean = false
) {
    val isValidPassword
        get() = hasMinLength && hasNumber && hasUppercaseCharacter && hasLowercaseCharacter
}
