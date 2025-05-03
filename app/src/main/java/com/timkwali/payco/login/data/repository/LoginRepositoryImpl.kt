package com.timkwali.payco.login.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.timkwali.payco.login.data.api.PaycoLoginApi
import com.timkwali.payco.login.data.api.model.LoginResponse
import com.timkwali.payco.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val dummyLoginApi: PaycoLoginApi
): LoginRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(email: String, password: String): LoginResponse {
        return dummyLoginApi.login(email, password)
    }
}