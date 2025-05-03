package com.timkwali.payco.addcard.data.di

import com.timkwali.payco.addcard.data.repository.AddCardRepositoryImpl
import com.timkwali.payco.addcard.domain.repository.AddCardRepository
import com.timkwali.payco.addcard.domain.usecase.AddNewCard
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addCardModule = module {
    single<AddCardRepository> { AddCardRepositoryImpl(get()) }

    single { AddNewCard(get()) }

    viewModel{ AddCardViewModel(get()) }
}