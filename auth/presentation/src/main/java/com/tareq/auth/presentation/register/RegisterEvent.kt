package com.tareq.auth.presentation.register

import com.tareq.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data object LoginTextButtonClicked: RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}