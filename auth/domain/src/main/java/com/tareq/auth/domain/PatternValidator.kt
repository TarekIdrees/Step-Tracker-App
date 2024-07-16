package com.tareq.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}