package com.dogak.dogakbackend.app.member.api

import com.dogak.dogakbackend.app.member.application.MemberService
import com.dogak.dogakbackend.app.member.dto.KakaoAccessToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/session")
class SessionController(
    private val memberService: MemberService
) {
    @PostMapping("/login")
    fun login(@RequestBody kakaoAccessToken: KakaoAccessToken, req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        val token = memberService.generateTokenByLogin(kakaoAccessToken)

        return ResponseEntity.ok(token)
    }
}