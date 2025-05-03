package com.timkwali.payco.addcard.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import com.timkwali.payco.addcard.domain.repository.AddCardRepository
import com.timkwali.payco.core.data.api.model.UserCard
import com.timkwali.payco.core.utils.Resource
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.YearMonth

class AddNewCard(
    private val addCardRepository: AddCardRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        cardNumber: String,
        cvv: String,
        expiryDate: String
    ) = flow<Resource<UserCard?>> {
        emit(Resource.Loading())

        try {
            if(!isCardNumberValid(cardNumber)) throw Exception("Please provide a valid 16 digit Card number.")
            if(!isCvvValid(cvv)) throw Exception("Please provide a valid CVV.")
            if(!isExpiryDateValid(expiryDate)) throw Exception("Please provide a valid expiry date.")

            emit(Resource.Success(addCardRepository.addCard(cardNumber, cvv, expiryDate)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    private fun isCardNumberValid(cardNumber: String): Boolean {
        return cardNumber.length == 16 && cardNumber.isDigitsOnly()
    }

    private fun isCvvValid(cvv: String): Boolean {
        return cvv.length == 3 && cvv.isDigitsOnly()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isExpiryDateValid(expiryDate: String): Boolean {
        try {
            val parts = expiryDate.split("/")
            if (parts.size != 2) return false

            val month = parts[0].toIntOrNull() ?: return false
            val year = parts[1].toIntOrNull() ?: return false

            if (month !in 1..12) return false

            val fullYear = if (year < 100) 2000 + year else year

            val today = LocalDate.now()
            val lastDayOfExpiryMonth = LocalDate.of(fullYear, month, 1).withDayOfMonth(
                YearMonth.of(fullYear, month).lengthOfMonth()
            )

            return lastDayOfExpiryMonth >= today
        } catch (e: Exception) {
            return false
        }
    }
}