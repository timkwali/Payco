package com.timkwali.payco.login.domain.repository

import com.timkwali.payco.login.data.api.model.LoginResponse

interface LoginRepository {
    suspend fun login(email: String, password: String): LoginResponse
}