package com.nbk.babek.helpers

import com.nbk.babek.authentication.jwt.JwtService
import com.nbk.babek.repository.UserRepository
import com.nbk.babek.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Service
class AuthenticatedUserProvider(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
) {
    fun getToken(): String {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val header = request.getHeader("Authorization")
        if (header == null || !header.startsWith("Bearer ")) {
            throw IllegalArgumentException("Missing or invalid Authorization header")
        }
        return header.substring(7)
    }

    fun getUsername(): String {
        val token = getToken()
        return jwtService.extractUsername(token)
    }

    fun getUserId(): Long {
        val token = getToken()
        return jwtService.extractUserId(token)
    }

    fun assertUserIdExists(): Long {
        val userId = getUserId()
        if (!userRepository.existsById(userId)) {
            throw UsernameNotFoundException("No user found with id: $userId")
        }
        return userId
    }
}

