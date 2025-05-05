package com.timkwali.payco.home.domain.usecase

import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.flow

class GetCards(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke() = flow<Resource<List<Card>>> {
        emit(Resource.Loading())

        try {
            val cards = homeRepository.getCards().map {
                Card(
                    id = it.id ?: 0,
                    cardNumber = it.cardNumber ?: "",
                    cvv = it.cvv ?: "",
                    expiryDate = it.expiryDate ?: "",
                    amount = it.amount ?: 0
                )
            }

            emit(Resource.Success(cards))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}