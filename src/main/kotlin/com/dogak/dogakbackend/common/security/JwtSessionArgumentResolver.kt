package com.dogak.dogakbackend.common.security

import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.domain.findByEmailWithCheck
import com.dogak.dogakbackend.common.config.WebConfig.Companion.JWT_COOKIE_NAME
import com.jayway.jsonpath.JsonPath
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

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
//        val path = String.format("$.%s", "info.email")
//
//        val request = webRequest.getNativeRequest(HttpServletRequest::class.java) ?: throw RuntimeException()
//
//        val cookie = HttpSupport.getCookie(request, JWT_COOKIE_NAME)
//
//        val claim = jwtService.parseClaim(cookie.value)
//        val email: String = JsonPath.parse(claim)
//            .read(path, paramType) as String



        return memberRepository.findByEmailWithCheck("shinjk213@nate.com")
    }
}