package com.tareq.core.data.auth

import com.tareq.core.domain.AuthInfo

fun AuthInfo.toAuthInfoSerializable() = AuthInfoSerializable(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)

fun AuthInfoSerializable.toAuthInfo() = AuthInfo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId
)