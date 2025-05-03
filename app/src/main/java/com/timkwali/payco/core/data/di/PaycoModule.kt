package com.timkwali.payco.core.data.di

import com.timkwali.payco.core.data.api.PaycoApi
import org.koin.dsl.module

val paycoModule = module {
    single { PaycoApi }
}