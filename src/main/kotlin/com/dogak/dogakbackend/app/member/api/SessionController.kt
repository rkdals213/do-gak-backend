package com.dogak.dogakbackend.app.member.api

import com.dogak.dogakbackend.common.config.WebConfig.Companion.EXPIRATION
import com.dogak.dogakbackend.common.config.WebConfig.Companion.JWT_COOKIE_NAME
import com.dogak.dogakbackend.app.member.dto.KakaoLoginPayload
import com.dogak.dogakbackend.app.member.application.MemberService
import com.dogak.dogakbackend.common.security.CookieConfig
import com.dogak.dogakbackend.common.security.DefaultJwtService
import com.dogak.dogakbackend.common.security.HttpSupport
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/session")
class SessionController(
    private val memberService: MemberService,
    private val defaultJwtService: DefaultJwtService
) {
    @PostMapping("/login")
    fun login(@RequestBody kakaoLoginPayload: KakaoLoginPayload, req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        val payload = memberService.login(kakaoLoginPayload)
        val token = defaultJwtService.create(payload)

        val cookie = HttpSupport.createCookie(
            CookieConfig(
                name = JWT_COOKIE_NAME,
                value = token,
                expires = EXPIRATION,
                secure = "https" == req.scheme
            )
        )

        res.addCookie(cookie)

        return ResponseEntity.ok()
            .build()
    }
}