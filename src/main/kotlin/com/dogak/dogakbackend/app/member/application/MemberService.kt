package com.dogak.dogakbackend.app.member.application

import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.KakaoLoginPayload
import com.dogak.dogakbackend.common.config.WebConfig.Companion.EXPIRATION
import com.dogak.dogakbackend.common.security.MemberPayload
import com.dogak.dogakbackend.common.security.Payload
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun login(kakaoLoginPayload: KakaoLoginPayload): Payload {
        val memberPayload = findMemberOrRegister(kakaoLoginPayload)
            .memberPayload()

        return createPayload(memberPayload)
    }

    private fun findMemberOrRegister(kakaoLoginPayload: KakaoLoginPayload): Member {
        return memberRepository.findByEmail(kakaoLoginPayload.email) ?: memberRepository.save(Member(kakaoLoginPayload.email, kakaoLoginPayload.name))
    }

    fun changeName(member: Member, changeMemberName: ChangeMemberName): Payload {
        member.changeName(changeMemberName)
        return createPayload(member.memberPayload())
    }

    private fun createPayload(memberPayload: MemberPayload) = Payload(LocalDateTime.now().plusSeconds(EXPIRATION.toLong()), memberPayload)
}