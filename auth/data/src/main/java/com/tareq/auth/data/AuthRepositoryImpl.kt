package com.tareq.auth.data

import com.tareq.auth.domain.AuthRepository
import com.tareq.core.data.networking.post
import com.tareq.core.domain.util.DataError
import com.tareq.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(email = email, password = password)
        )
    }
}