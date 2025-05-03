package com.timkwali.payco.login.domain.usecase

import android.util.Patterns
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.login.data.api.model.LoginResponse
import com.timkwali.payco.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.flow

class Login (
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(
        email: String,
        password: String
    ) = flow<Resource<LoginResponse>> {
        emit(Resource.Loading())

        try {
            if(email.isEmpty() || !isValidEmail(email)) throw Exception("Please provide a valid email address.")
            if(password.isEmpty()) throw Exception("Password cannot be empty.")

            emit(Resource.Success(loginRepository.login(email, password)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}