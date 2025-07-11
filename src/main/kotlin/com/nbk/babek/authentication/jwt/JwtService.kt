package com.nbk.babek.authentication.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtService(
    @Value("\${jwt-secret}")
    private val jwtSecret: String
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtSecret.encodeToByteArray())
    private val expirationMs: Long = 1000 * 60 * 60 * 24

    fun generateToken(username: String, userId: Long?): String {
        val now = Date()
        val expiry = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(username)
            .claim("id", userId)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun isTokenValid(token: String, username: String): Boolean {
        return try {
            extractUsername(token) == username
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserId(token: String): Long =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .get("id", Integer::class.java)
            .toLong()


}