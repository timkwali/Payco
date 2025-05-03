package com.timkwali.payco.login.data.di

import com.timkwali.payco.login.data.api.PaycoLoginApi
import com.timkwali.payco.login.data.repository.LoginRepositoryImpl
import com.timkwali.payco.login.domain.repository.LoginRepository
import com.timkwali.payco.login.domain.usecase.Login
import com.timkwali.payco.login.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    single { PaycoLoginApi() }

    single<LoginRepository> { LoginRepositoryImpl(get()) }

    single { Login(get()) }

    viewModel { LoginViewModel(get()) }
}