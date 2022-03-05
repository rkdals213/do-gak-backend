package com.dogak.dogakbackend.common.security

import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.common.http.BearerHeader
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

private const val BEARER = "Bearer"
private const val INFO_EMAIL_PATH = "info.email"

@Component
class JwtSessionArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
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
        val token = extractBearerToken(webRequest) ?: return Member.DUMMY

        if (jwtTokenProvider.isValidToken(token).not()) {
            throw RuntimeException("로그인 정보가 정확하지 않습니다")
        }

        val email = jwtTokenProvider.getEmail(token)

        return memberRepository.findByEmail(email) ?: Member.DUMMY
    }

    private fun extractBearerToken(request: NativeWebRequest): String? {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        val (tokenType, token) = BearerHeader.splitToTokenFormat(authorization)
        if (tokenType != BEARER) {
            throw IllegalAccessException()
        }
        return token
    }
}