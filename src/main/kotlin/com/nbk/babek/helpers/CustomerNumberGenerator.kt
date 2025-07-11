package com.nbk.babek.helpers

import com.nbk.babek.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class CustomerNumberGenerator(
    private val customerRepository: CustomerRepository,
) {

    fun generateSecureCustomerNumber(): Int {
        val secureRandom = SecureRandom()
        val firstDigit = secureRandom.nextInt(9) + 1
        val remainingDigits = (1..8)
            .map { secureRandom.nextInt(10) }
            .joinToString("")
        val customerNumberStr = "$firstDigit$remainingDigits"
        return customerNumberStr.toInt()
    }

    fun generateUniqueCustomerNumber(): Int {
        var customerNumber: Int
        do {
            customerNumber = generateSecureCustomerNumber()
        } while (customerRepository.existsByCustomerNumber(customerNumber))
        return customerNumber
    }
}