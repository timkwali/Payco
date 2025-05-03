package com.timkwali.payco.core

import android.app.Application
import com.timkwali.payco.addcard.data.di.addCardModule
import com.timkwali.payco.carddetails.data.di.cardDetailsModule
import com.timkwali.payco.core.data.di.paycoModule
import com.timkwali.payco.home.data.di.homeModule
import com.timkwali.payco.login.data.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PaycoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PaycoApplication)
            modules(paycoModule, loginModule, homeModule, addCardModule, cardDetailsModule)
        }
    }
}