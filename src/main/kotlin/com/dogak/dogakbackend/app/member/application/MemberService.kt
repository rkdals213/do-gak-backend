package com.dogak.dogakbackend.app.member.application

import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.domain.findByEmailWithCheck
import com.dogak.dogakbackend.app.member.dto.KakaoLoginPayload
import com.dogak.dogakbackend.common.security.MemberPayload
import com.dogak.dogakbackend.common.security.Payload
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun login(kakaoLoginPayload: KakaoLoginPayload): Payload {
        val memberPayload = memberRepository.findByEmailWithCheck(kakaoLoginPayload.email)
            .memberPayload()

        return createPayload(memberPayload)
    }

    private fun createPayload(memberPayload: MemberPayload) = Payload(/*LocalDateTime.now().plusSeconds(EXPIRATION.toLong()),*/ memberPayload)
}