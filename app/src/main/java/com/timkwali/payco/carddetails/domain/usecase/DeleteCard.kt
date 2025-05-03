package com.timkwali.payco.carddetails.domain.usecase

import com.timkwali.payco.carddetails.domain.repository.CardDetailsRepository
import com.timkwali.payco.core.utils.Resource
import kotlinx.coroutines.flow.flow

class DeleteCard(
    private val cardDetailsRepository: CardDetailsRepository
) {
    suspend operator fun invoke(id: Int) = flow<Resource<Boolean>> {
        emit(Resource.Loading())

        try {
            val response = cardDetailsRepository.deleteCard(id)
            if(!response) throw Exception("Error deleting Card")
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}