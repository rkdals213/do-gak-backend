package com.dogak.dogakbackend.app.member.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class KakaoLoginPayload(
    @field:Email
    val email: String,
    @field:NotBlank
    val name: String
)