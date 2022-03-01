package com.dogak.dogakbackend.app.member.dto

import javax.validation.constraints.NotBlank

data class KakaoAccessToken(
    @field:NotBlank
    val accessToken: String
)