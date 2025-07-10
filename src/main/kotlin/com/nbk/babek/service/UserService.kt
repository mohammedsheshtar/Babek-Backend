package com.nbk.babek.service

import com.nbk.babek.repository.UserEntity
import com.nbk.babek.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun create(user: UserEntity): UserEntity {

        val existingUser = userRepository.findByUsername(user.username)
        if (existingUser != null) {
            throw IllegalArgumentException("Username already exists.")
        }

        val username = user.username.trim()

        if (username.isBlank()) {
            throw IllegalArgumentException("Username cannot be blank or empty.")
        }

        if (username.length < 5) {
            throw IllegalArgumentException("Username must be at least 5 characters long.")
        }

        if (username.length > 20) {
            throw IllegalArgumentException("Username cannot exceed 20 characters.")
        }

        if (!username.matches(Regex("^[a-zA-Z0-9_]+$"))) {
            throw IllegalArgumentException("Username can only contain letters, numbers, and underscores.")
        }


        return userRepository.save(user.copy(username = username))
    }

    fun getAll(): List<UserEntity> {
        return userRepository.findAll()
    }
}