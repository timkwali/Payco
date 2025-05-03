package com.timkwali.payco.login.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.timkwali.payco.core.utils.SIMULATED_NETWORK_DELAY
import com.timkwali.payco.login.data.api.model.LoginResponse
import kotlinx.coroutines.delay
import java.time.LocalDate

class PaycoLoginApi {


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun login(email: String, password: String): LoginResponse {
        delay(SIMULATED_NETWORK_DELAY)
        return LoginResponse(
            name = email.split("@").first(),
            email = email,
            lastLogin = LocalDate.now().toString()
        )
    }
}