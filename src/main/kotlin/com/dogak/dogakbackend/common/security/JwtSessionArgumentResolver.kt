package com.dogak.dogakbackend.common.security

import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.domain.findByEmailWithCheck
import com.dogak.dogakbackend.common.config.WebConfig.Companion.JWT_COOKIE_NAME
import com.jayway.jsonpath.JsonPath
import io.jsonwebtoken.Jwts
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

private const val BEARER = "Bearer"

@Component
class JwtSessionArgumentResolver(
    private val jwtService: JwtService,
    private val memberRepository: MemberRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(MemberClaim::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val paramType = parameter.parameterType
        val token = extractBearerToken(webRequest) ?: return Member(0, "", "")

        val path = String.format("$.%s", "info.email")
        val claim = jwtService.parseClaim(token)
        val email: String = JsonPath.parse(claim)
            .read(path, paramType) as String

        return memberRepository.findByEmail(email) ?: Member(0, "", "")
    }

    private fun extractBearerToken(request: NativeWebRequest): String? {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        val (tokenType, token) = splitToTokenFormat(authorization)
        if (tokenType != BEARER) {
            throw IllegalAccessException()
        }
        return token
    }

    private fun splitToTokenFormat(authorization: String): Pair<String, String> {
        return try {
            val tokenFormat = authorization.split(" ")
            tokenFormat[0] to tokenFormat[1]
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalAccessException()
        }
    }
}