package com.timkwali.payco.payment.domain.usecase

import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class GetAmountOwed(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke() = flow<Resource<Int>> {
        emit(Resource.Loading())

        try {
            emit(Resource.Success(paymentRepository.getAmountToPay()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}