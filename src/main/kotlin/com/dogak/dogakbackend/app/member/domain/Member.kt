package com.dogak.dogakbackend.app.member.domain

import com.dogak.dogakbackend.common.security.MemberPayload
import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false, length = 30)
    val name: String
) {
    constructor(email: String, name: String) : this(0, email, name)

    fun memberPayload(): MemberPayload = MemberPayload(id, email, name)
}