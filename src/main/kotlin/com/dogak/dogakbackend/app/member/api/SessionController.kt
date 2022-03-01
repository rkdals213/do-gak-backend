package com.dogak.dogakbackend.app.member.api

import com.dogak.dogakbackend.app.member.application.MemberService
import com.dogak.dogakbackend.app.member.dto.KakaoAccessToken
import com.dogak.dogakbackend.common.security.CookieManager
import com.dogak.dogakbackend.common.security.DefaultJwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/session")
class SessionController(
    private val memberService: MemberService,
    private val defaultJwtService: DefaultJwtService,
    private val cookieManager: CookieManager
) {
    @PostMapping("/login")
    fun login(@RequestBody kakaoAccessToken: KakaoAccessToken, req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        val payload = memberService.login(kakaoAccessToken)
        val token = defaultJwtService.create(payload)

        return ResponseEntity.ok(token)
    }

    @DeleteMapping("/logout")
    fun logout(req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        println(req.headerNames)
        cookieManager.removeCookie(req, res)

        return ResponseEntity.ok()
            .build()
    }
}