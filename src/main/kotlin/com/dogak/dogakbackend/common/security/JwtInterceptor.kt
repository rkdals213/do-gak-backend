package com.dogak.dogakbackend.common.security

import com.dogak.dogakbackend.common.http.BearerHeader
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val BEARER = "Bearer"

class JwtInterceptor(private val jwtService: JwtService) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod || !isAuthenticationPresent(handler)) {
            return true
        }

        val token = extractBearerToken(request)

        return jwtService.isUsable(token)
    }

    private fun isAuthenticationPresent(handler: HandlerMethod): Boolean {
        return (handler.hasMethodAnnotation(Authenticated::class.java)
                || handler.beanType.isAnnotationPresent(Authenticated::class.java))
    }

    private fun extractBearerToken(request: HttpServletRequest): String {
        val authorization = request.getHeader(AUTHORIZATION) ?: throw IllegalAccessException()
        val (tokenType, token) = BearerHeader.splitToTokenFormat(authorization)
        if (tokenType != BEARER) {
            throw IllegalAccessException()
        }
        return token
    }
}