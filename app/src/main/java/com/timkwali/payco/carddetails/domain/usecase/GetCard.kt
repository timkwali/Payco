package com.timkwali.payco.carddetails.domain.usecase

import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.carddetails.domain.repository.CardDetailsRepository
import com.timkwali.payco.core.utils.Resource
import kotlinx.coroutines.flow.flow

class GetCard(
    private val cardDetailsRepository: CardDetailsRepository
) {
    suspend operator fun invoke(id: Int) = flow<Resource<Card>> {
        emit(Resource.Loading())

        try {
            val response = cardDetailsRepository.getCardById(id) ?: throw Exception("Card not found")
            val card = response.run {
                Card(
                    id = this.id ?: 0,
                    cardNumber = cardNumber ?: "",
                    cvv = cvv ?: "",
                    expiryDate = expiryDate ?: "",
                    amount = amount ?: 0
                )
            }

            emit(Resource.Success(card))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}