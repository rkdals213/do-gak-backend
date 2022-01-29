package com.dogak.dogakbackend.common.security

data class Payload(
//    val exp: LocalDateTime,
    val memberPayload: MemberPayload
)

data class MemberPayload(
    val id: Long,
    val email: String,
    val name: String
)