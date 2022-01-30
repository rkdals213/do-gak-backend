package com.dogak.dogakbackend.app.member.api

import com.dogak.dogakbackend.app.member.application.MemberService
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.MemberInfo
import com.dogak.dogakbackend.common.config.WebConfig
import com.dogak.dogakbackend.common.security.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
    private val defaultJwtService: DefaultJwtService
) {

    @Authenticated
    @GetMapping("/myInfo")
    fun findMyInfo(@MemberClaim member: Member): ResponseEntity<Any> {
        return ResponseEntity.ok(MemberInfo(member))
    }

    @Authenticated
    @PatchMapping("/name")
    fun changeName(@MemberClaim member: Member, @RequestBody changeMemberName: ChangeMemberName, req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        val payload = memberService.changeName(member, changeMemberName)
        val token = defaultJwtService.create(payload)

        val cookie = HttpSupport.createCookie(
            CookieConfig(
                name = WebConfig.JWT_COOKIE_NAME,
                value = token,
                expires = WebConfig.EXPIRATION,
                secure = "https" == req.scheme
            )
        )

        res.addCookie(cookie)

        return ResponseEntity.ok()
            .build()
    }
}