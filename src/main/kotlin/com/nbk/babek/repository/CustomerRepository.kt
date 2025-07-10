package com.nbk.babek.repository

import jakarta.inject.Named
import org.springframework.data.jpa.repository.JpaRepository
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Named
interface CustomerRepository : JpaRepository<CustomerEntity, Long> {

}

@Entity
@Table(name = "customers")
data class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val customerNumber: Int,
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: String
) {
    constructor() : this(null, 0, "", LocalDate.now(), "")
}