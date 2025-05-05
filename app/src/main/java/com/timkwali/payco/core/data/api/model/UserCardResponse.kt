package com.timkwali.payco.core.data.api.model

data class UserCardResponse(
    val id: Int?,
    val cardNumber: String?,
    val cvv: String?,
    val expiryDate: String?,
    val amount: Int?
)
