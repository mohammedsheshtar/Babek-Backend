package com.nbk.babek.dto

import java.time.LocalDate

data class CustomerRequest(
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: String
)

data class CustomerResponse(
    val customerNumber: Int,
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: String
)

data class UpdateCustomerRequest(
    val customerName: String? = null,
    val dateOfBirth: LocalDate? = null,
    val gender: String? = null
)
