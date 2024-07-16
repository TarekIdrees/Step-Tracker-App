package com.tareq.core.presentation.ui

import com.tareq.core.domain.util.DataError

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )

        DataError.Network.NO_INTERNET_CONNECTION -> UiText.StringResource(
            R.string.error_no_internet_connection
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.error_unknown
        )

        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.error_unauthorized
        )

        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.error_email_exist
        )
    }
}