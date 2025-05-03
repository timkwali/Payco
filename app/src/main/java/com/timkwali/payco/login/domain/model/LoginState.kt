package com.timkwali.payco.login.domain.model

import com.timkwali.payco.login.data.api.model.LoginResponse

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null
)
