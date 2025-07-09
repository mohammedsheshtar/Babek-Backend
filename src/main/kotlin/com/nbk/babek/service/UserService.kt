package com.nbk.babek.service

import com.nbk.babek.repository.UserEntity
import com.nbk.babek.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun create(user: UserEntity): UserEntity {
        return userRepository.save(user)
    }

    fun getAll(): List<UserEntity> {
        return userRepository.findAll()
    }
}