package com.nbk.babek.service

import com.nbk.babek.helpers.UserHelpers
import com.nbk.babek.repository.UserEntity
import com.nbk.babek.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val helpers: UserHelpers,
    private val passwordEncoder: PasswordEncoder
) {
    fun create(user: UserEntity): UserEntity {

        helpers.validateUsername(user.username)
        helpers.validatePassword(user.password)

        return userRepository.save(user.copy(username = user.username, password = passwordEncoder.encode(user.password)))
    }

    fun getAll(): List<UserEntity> {
        return userRepository.findAll()
    }
}