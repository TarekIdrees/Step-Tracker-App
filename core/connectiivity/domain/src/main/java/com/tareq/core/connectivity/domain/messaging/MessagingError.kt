package com.tareq.core.connectivity.domain.messaging

import com.tareq.core.domain.util.Error


enum class MessagingError: Error {
    CONNECTION_INTERRUPTED,
    DISCONNECTED,
    UNKNOWN
}