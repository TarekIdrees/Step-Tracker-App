package com.tareq.auth.domain

import com.tareq.core.domain.util.DataError
import com.tareq.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}