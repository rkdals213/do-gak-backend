package com.dogak.dogakbackend.app.member.dto

import java.time.ZonedDateTime

data class KakaoMemberInfo(
    val id: String,
    val connected_at: ZonedDateTime,
    val properties: Properties,
    val kakao_account: KakaoAccount
)

data class Properties(
    val nickname: String
)

data class KakaoAccount(
    val profile_nickname_needs_agreement: Boolean,
    val profile: Profile,
    val has_email: Boolean,
    val email_needs_agreement: Boolean,
    val is_email_valid: Boolean,
    val is_email_verified: Boolean,
    val email: String
)

data class Profile(
    val nickname: String
)