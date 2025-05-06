package com.timkwali.payco.core.utils

import android.util.Patterns

fun Int.formatAmount(): String {
    return this.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.isNotEmpty()
}

fun encryptPassword(password: String): String {
    var encryptedPassword = ""
    password.forEach { _ -> encryptedPassword += '*' }
    return encryptedPassword
}