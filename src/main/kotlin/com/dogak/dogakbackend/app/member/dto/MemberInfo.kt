package com.dogak.dogakbackend.app.member.dto

import com.dogak.dogakbackend.app.member.domain.Member

data class MemberInfo(
    val email: String,
    val name: String
) {
    constructor(member: Member) : this(member.email, member.name)
}