package com.nbk.babek.helpers

import com.nbk.babek.dto.CustomerRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerValidator {

    fun validateRequest(request: CustomerRequest) {
        if (request.customerName.isBlank()) {
            throw IllegalArgumentException("Customer name cannot be blank")
        }
        if (request.customerName.length > 255) {
            throw IllegalArgumentException("Customer name cannot exceed 255 characters")
        }
        if (!request.customerName.matches(Regex("^[a-zA-Z\\s]+$"))) {
            throw IllegalArgumentException("Customer name can only contain letters and spaces")
        }

        if (request.dateOfBirth.isAfter(LocalDate.now())) {
            throw IllegalArgumentException("Date of birth cannot be in the future")
        }

        if (request.gender.uppercase() !in listOf("M", "F")) {
            throw IllegalArgumentException("Gender must be 'M' or 'F'")
        }
    }
}

