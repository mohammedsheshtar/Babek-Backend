package com.nbk.babek.service

import com.nbk.babek.dto.CustomerRequest
import com.nbk.babek.dto.CustomerResponse
import com.nbk.babek.dto.UpdateCustomerRequest
import com.nbk.babek.helpers.CustomerNumberGenerator
import com.nbk.babek.helpers.CustomerValidator
import com.nbk.babek.repository.CustomerEntity
import com.nbk.babek.repository.CustomerRepository
import com.nbk.babek.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val userRepository: UserRepository,
    private val customerNumberGenerator: CustomerNumberGenerator,
    private val customerRepository: CustomerRepository,
    private val customerValidator : CustomerValidator
) {
    fun addCustomers(request: CustomerRequest): CustomerResponse {

        val customerNumber = customerNumberGenerator.generateUniqueCustomerNumber()

        customerValidator.validateRequest(request)

        val newCustomer = customerRepository.save(CustomerEntity(
            customerNumber = customerNumber,
            customerName = request.customerName,
            dateOfBirth = request.dateOfBirth,
            gender = request.gender
        ))

        return CustomerResponse(
            customerNumber = newCustomer.customerNumber,
            customerName = newCustomer.customerName,
            dateOfBirth = newCustomer.dateOfBirth,
            gender = newCustomer.gender
        )
    }

    fun updateCustomer(customerNumber: Int, request: UpdateCustomerRequest): CustomerResponse {
        val existingCustomer = customerRepository.findByCustomerNumber(customerNumber)
            ?: throw IllegalArgumentException("Customer with number $customerNumber does not exist")

        customerValidator.validateRequest(request)

        val updatedCustomer = existingCustomer.copy(
            customerName = request.customerName ?: existingCustomer.customerName,
            dateOfBirth = request.dateOfBirth ?: existingCustomer.dateOfBirth,
            gender = request.gender ?: existingCustomer.gender
        )


        customerRepository.save(updatedCustomer)

        return CustomerResponse(
            customerNumber = updatedCustomer.customerNumber,
            customerName = updatedCustomer.customerName,
            dateOfBirth = updatedCustomer.dateOfBirth,
            gender = updatedCustomer.gender
        )
    }

    fun listCustomers(): List<CustomerResponse> {
        return customerRepository.findAll().map {
            CustomerResponse(
                customerNumber = it.customerNumber,
                customerName = it.customerName,
                dateOfBirth = it.dateOfBirth,
                gender = it.gender
            )
        }
    }

}