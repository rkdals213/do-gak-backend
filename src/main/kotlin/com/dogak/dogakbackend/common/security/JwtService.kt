package com.dogak.dogakbackend.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.ZoneOffset
import java.util.*

interface JwtService {
    fun create(payload: Payload): String
    fun isUsable(token: String): Boolean
    fun parseClaim(token: String): String
}

@Component
class DefaultJwtService : JwtService {
    private val jsonMapper = ObjectMapper()

    private val secretKey: Key
        get() = try {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(JWT_KEY)
            md.update(JWT_KEY_SALT)
            Keys.hmacShaKeyFor(md.digest())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

    override fun create(payload: Payload): String = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setExpiration(Date.from(payload.exp.toInstant(ZoneOffset.UTC)))
        .signWith(secretKey)
        .claim("info", payload.memberPayload)
        .compact()

    override fun isUsable(token: String): Boolean {
        return checkJwt(token)
    }

    override fun parseClaim(token: String): String {
        return parseJwt(token)!!
    }

    private fun checkJwt(token: String): Boolean {
        return try {
            val claims: Jws<Claims> = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            val expiration = claims.body.expiration
            println(expiration)
            println(claims.body.expiration)
            System.currentTimeMillis() <= expiration.time
        } catch (e: Exception) {
            false
        }
    }

    private fun parseJwt(token: String): String? {
        return try {
            val claims: Jws<Claims> = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            jsonMapper.writeValueAsString(claims.body)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private val JWT_KEY_SALT: ByteArray = "qlalfxhzmsthrma".toByteArray(StandardCharsets.UTF_8)
        private val JWT_KEY: ByteArray = "qlalfxhzms".toByteArray(StandardCharsets.UTF_8)
    }
}