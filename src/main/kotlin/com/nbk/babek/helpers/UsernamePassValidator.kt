package com.nbk.babek.helpers

import com.nbk.babek.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UsernamePassValidator(
    private val userRepository: UserRepository,
) {
    fun validateUsername(username: String) {
        if (username.isBlank()) {
            throw IllegalArgumentException("Username cannot be blank.")
        }
        if (username.length !in 5..20) {
            throw IllegalArgumentException("Username must be between 5 and 20 characters.")
        }
        if (!Regex("^[a-zA-Z0-9_]+$").matches(username)) {
            throw IllegalArgumentException("Username can only contain letters, numbers, and underscores.")
        }

        val existingUser = userRepository.findByUsername(username)
        if (existingUser != null) {
            throw IllegalArgumentException("Username already exists.")
        }
    }

    fun validatePassword(password: String) {
        if (password.length !in 8..20) {
            throw IllegalArgumentException("Password must be between 8 and 20 characters.")
        }
        if (!Regex("^(?=.*[A-Za-z])(?=.*\\d).+$").matches(password)) {
            throw IllegalArgumentException("Password must contain at least one letter and one number.")
        }
        if (!Regex("^(?=.*[!@#\$%^&*(),.?\":{}|<>]).+$").matches(password)) {
            throw IllegalArgumentException("Password must contain at least one special character.")
        }
        if (!Regex("^(?=.*[a-z])(?=.*[A-Z]).+$").matches(password)) {
            throw IllegalArgumentException("Password must contain both uppercase and lowercase letters.")
        }
        if (password.contains(" ")) {
            throw IllegalArgumentException("Password must not contain spaces.")
        }
    }
}