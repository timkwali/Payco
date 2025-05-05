package com.timkwali.payco.login.domain.usecase

import android.util.Patterns
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.core.utils.isValidEmail
import com.timkwali.payco.core.utils.isValidPassword
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
            if(!isValidEmail(email)) throw Exception("Please provide a valid email address.")
            if(!isValidPassword(password)) throw Exception("Password cannot be empty.")

            emit(Resource.Success(loginRepository.login(email, password)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}