package com.dogak.dogakbackend.app.member.dto

import javax.validation.constraints.NotBlank

data class ChangeMemberName(
    @NotBlank
    val name: String
)
