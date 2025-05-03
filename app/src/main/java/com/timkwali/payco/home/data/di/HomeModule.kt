package com.timkwali.payco.home.data.di

import com.timkwali.payco.home.data.repository.HomeRepositoryImpl
import com.timkwali.payco.home.domain.repository.HomeRepository
import com.timkwali.payco.home.domain.usecase.GetCards
import com.timkwali.payco.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    single { GetCards(get()) }

    viewModel { HomeViewModel(get()) }
}