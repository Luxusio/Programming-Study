package org.example.springoauth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.example.springoauth.config.AccessRefreshToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest

@Service
class MyTokenService(
    @Value("\${app.jwt.secret}") secretKey: String,
) {
    private val secretKey = secretKey.toSecretKey(SignatureAlgorithm.HS256)

    private val parser = Jwts.parserBuilder()
        .setSigningKey(secretKey.encodeToByteArray())
        .build()

    fun getAuthenticationIfValid(request: HttpServletRequest): Authentication? {
        val authorization = request.getHeader("Authorization") ?: return null
        if (!authorization.startsWith("Bearer ")) {
            return null
        }

        val token = authorization.removePrefix("Bearer ")
        return try {
            val result = parser.parseClaimsJwt(token)
            val subject = result.body.subject
            UsernamePasswordAuthenticationToken(MemberInfo(subject), null, it.authorities)
        } catch (e: JwtException) {
            null
        }
    }

    fun generateToken(email: String, s: String): AccessRefreshToken {
        return AccessRefreshToken(
            generateJwt(email, Duration.ofMinutes(10)),
            generateJwt(email, Duration.ofMinutes(30)),
        )
    }

    private fun generateJwt(subject: String, duration: Duration): String {
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(LocalDateTime.now().toDate())
            .setExpiration((LocalDateTime.now() + duration).toDate())
            .signWith(secretKey)
            .compact()
    }

}

private fun String.toSecretKey(algorithm: SignatureAlgorithm): SecretKey {
    val decodedKey = this.toByteArray()
    return SecretKeySpec(decodedKey, 0, decodedKey.size, algorithm.jcaName)
}

fun LocalDateTime.toDate(): Date = Timestamp.valueOf(this)
