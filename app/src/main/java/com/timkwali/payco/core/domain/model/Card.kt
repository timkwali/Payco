package com.timkwali.payco.core.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import com.timkwali.payco.core.utils.formatAmount
import java.time.LocalDate
import java.time.YearMonth

data class Card(
    val id: Int,
    val cardNumber: String,
    val cvv: String,
    val expiryDate: String,
    val amount: Int
) {
    val spacedNumber = cardNumber.chunked(4).joinToString(" ")
    val maskedNumber = "**** **** **** ${cardNumber.takeLast(4)}"
    val typeOfCard = getCardType()
    val formatedAmount = amount.formatAmount()
    val formatedExpiryDate = expiryDate.formatExpiry()

    private fun getCardType(): CardType? {
        if(cardNumber.isEmpty()) return null
        if(!cardNumber.first().isDigit()) return null

        return if((cardNumber.first().digitToInt() % 2) == 0) {
            CardType.MasterCard
        } else CardType.Visa
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isExpiryDateValid(): Boolean {
        try {
            val date = if(expiryDate.contains('/')) expiryDate else formatedExpiryDate
            val parts = date.split("/")
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

    fun isCardNumberValid(): Boolean {
        return cardNumber.length == 16 && cardNumber.isDigitsOnly()
    }

    fun isCvvValid(): Boolean {
        return cvv.length == 3 && cvv.isDigitsOnly()
    }

    private fun String.formatExpiry(): String {
        return when {
            this.length <= 2 -> this
            else -> this.substring(0, 2) + "/" + this.substring(2)
        }
    }
}
