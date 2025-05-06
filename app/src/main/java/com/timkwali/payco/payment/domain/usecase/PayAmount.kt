package com.timkwali.payco.payment.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.flow

class PayAmount(
    private val paymentRepository: PaymentRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(card: Card?) = flow<Resource<Boolean>> {
        emit(Resource.Loading())

        try {
            if(card?.isCardNumberValid() != true) throw Exception("Please provide a valid 16 digit Card number.")
            if(card?.isCvvValid() != true) throw Exception("Please provide a valid CVV.")
            if(card?.isExpiryDateValid() != true) throw Exception("Please provide a valid expiry date.")

            val response = paymentRepository.payAmount(card)
            if(!response) throw Exception("Insufficient Card balance.")

            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}