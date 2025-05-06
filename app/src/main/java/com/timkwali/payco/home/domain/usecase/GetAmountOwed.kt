package com.timkwali.payco.home.domain.usecase

import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.flow

class GetAmountOwed(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = flow<Resource<Int>> {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(homeRepository.getAmountToPay()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}