package com.timkwali.payco.core.utils

fun Int.groupByThrees(): String {
    return this.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}