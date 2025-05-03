package com.timkwali.payco.carddetails.data.di

import com.timkwali.payco.carddetails.data.repository.CardDetailsRepositoryImpl
import com.timkwali.payco.carddetails.domain.repository.CardDetailsRepository
import com.timkwali.payco.carddetails.domain.usecase.DeleteCard
import com.timkwali.payco.carddetails.domain.usecase.GetCard
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cardDetailsModule = module {
    single<CardDetailsRepository> { CardDetailsRepositoryImpl(get()) }

    single { DeleteCard(get()) }

    single { GetCard(get()) }

    viewModel { CardDetailsViewModel(get(), get()) }
}