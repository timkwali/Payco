package com.timkwali.payco.payment.data.di

import com.timkwali.payco.payment.domain.usecase.GetAmountOwed
import com.timkwali.payco.payment.domain.usecase.GetCards
import com.timkwali.payco.payment.data.repository.PaymentRepositoryImpl
import com.timkwali.payco.payment.domain.repository.PaymentRepository
import com.timkwali.payco.payment.domain.usecase.PayAmount
import com.timkwali.payco.payment.presentation.viewmodel.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val paymentModule = module {
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }

    single { GetAmountOwed(get()) }

    single { GetCards(get()) }

    single { PayAmount(get()) }

    viewModel { PaymentViewModel(get(), get(), get()) }
}