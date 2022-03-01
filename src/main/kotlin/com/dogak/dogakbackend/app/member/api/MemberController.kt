package com.dogak.dogakbackend.app.member.api

import com.dogak.dogakbackend.app.member.application.MemberService
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.MemberInfo
import com.dogak.dogakbackend.common.security.Authenticated
import com.dogak.dogakbackend.common.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @Authenticated
    @GetMapping("/myInfo")
    fun findMyInfo(@MemberClaim member: Member): ResponseEntity<Any> {
        return ResponseEntity.ok(MemberInfo(member))
    }

    @Authenticated
    @PatchMapping("/name")
    fun changeName(@MemberClaim member: Member, @RequestBody changeMemberName: ChangeMemberName, req: HttpServletRequest, res: HttpServletResponse): ResponseEntity<Any> {
        val token = memberService.generateTokenByChangeName(member, changeMemberName)

        return ResponseEntity.ok(token)
    }
}