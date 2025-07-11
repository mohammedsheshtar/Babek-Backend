package com.nbk.babek.controller

import com.hazelcast.spi.impl.operationservice.impl.responses.Response
import com.nbk.babek.dto.CustomerRequest
import com.nbk.babek.dto.CustomerResponse
import com.nbk.babek.dto.UpdateCustomerRequest
import com.nbk.babek.helpers.AuthenticatedUserProvider
import com.nbk.babek.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class CustomerController(
    private val customerService: CustomerService,
    private val authenticatedUserProvider: AuthenticatedUserProvider
) {

    @PostMapping("/customers")
    fun addCustomers(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        authenticatedUserProvider.assertUserIdExists()

        return ResponseEntity.ok(customerService.addCustomers(request))

    }

    @PostMapping("/customers/{customerNumber}")
    fun updateCustomer(
        @PathVariable customerNumber: Int,
        @RequestBody request: UpdateCustomerRequest
    ): ResponseEntity<CustomerResponse> {
        authenticatedUserProvider.assertUserIdExists()

        return ResponseEntity.ok(customerService.updateCustomer(customerNumber, request))
    }

    @GetMapping("/customers")
    fun listCustomers(): ResponseEntity<List<CustomerResponse>> {
        authenticatedUserProvider.assertUserIdExists()

        return ResponseEntity.ok(customerService.listCustomers())
    }

}