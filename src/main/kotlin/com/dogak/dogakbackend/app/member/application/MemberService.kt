package com.dogak.dogakbackend.app.member.application

import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.KakaoAccessToken
import com.dogak.dogakbackend.app.member.dto.KakaoMemberInfo
import com.dogak.dogakbackend.common.config.WebConfig.Companion.EXPIRATION
import com.dogak.dogakbackend.common.http.KakaoClient
import com.dogak.dogakbackend.common.http.bearerHeader
import com.dogak.dogakbackend.common.security.MemberPayload
import com.dogak.dogakbackend.common.security.Payload
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val kakaoClient: KakaoClient
) {
    fun login(kakaoAccessToken: KakaoAccessToken): Payload {
        val kakaoMemberInfo = kakaoClient.getUserInfo(bearerHeader(kakaoAccessToken.accessToken))
        val member = findMemberOrRegister(kakaoMemberInfo)
        member.validateLoginInfo(kakaoMemberInfo)

        return createPayload(member.memberPayload())
    }

    private fun findMemberOrRegister(kakaoMemberInfo: KakaoMemberInfo): Member {
        return memberRepository.findByEmail(kakaoMemberInfo.kakao_account.email) ?: memberRepository.save(Member(kakaoMemberInfo.kakao_account.email, kakaoMemberInfo.kakao_account.profile.nickname))
    }

    fun changeName(member: Member, changeMemberName: ChangeMemberName): Payload {
        member.changeName(changeMemberName)
        return createPayload(member.memberPayload())
    }

    private fun createPayload(memberPayload: MemberPayload) = Payload(LocalDateTime.now().plusSeconds(EXPIRATION.toLong()), memberPayload)
}