package com.dogak.dogakbackend.app.member.application

import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.KakaoAccessToken
import com.dogak.dogakbackend.app.member.dto.KakaoMemberInfo
import com.dogak.dogakbackend.common.http.BearerHeader
import com.dogak.dogakbackend.common.http.KakaoClient
import com.dogak.dogakbackend.common.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val kakaoClient: KakaoClient,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun generateTokenByLogin(kakaoAccessToken: KakaoAccessToken): String {
        val kakaoMemberInfo = kakaoClient.getUserInfo(BearerHeader.of(kakaoAccessToken.accessToken))
        val member = findMemberOrRegister(kakaoMemberInfo)
        member.validateLoginInfo(kakaoMemberInfo)

        return jwtTokenProvider.createToken(member.email)
    }

    private fun findMemberOrRegister(kakaoMemberInfo: KakaoMemberInfo): Member {
        return memberRepository.findByEmail(kakaoMemberInfo.kakao_account.email) ?: memberRepository.save(Member(kakaoMemberInfo.kakao_account.email, kakaoMemberInfo.kakao_account.profile.nickname))
    }

    @Transactional
    fun generateTokenByChangeName(member: Member, changeMemberName: ChangeMemberName): String {
        member.changeName(changeMemberName)
        return jwtTokenProvider.createToken(member.email)
    }
}