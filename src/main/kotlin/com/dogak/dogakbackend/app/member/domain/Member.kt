package com.dogak.dogakbackend.app.member.domain

import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.common.infra.TableTimeStamp
import com.dogak.dogakbackend.common.security.MemberPayload
import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false, length = 30)
    var name: String
) {
    val tableTimeStamp: TableTimeStamp = TableTimeStamp()

    constructor(email: String, name: String) : this(0, email, name)

    fun memberPayload(): MemberPayload = MemberPayload(id, email, name)

    fun changeName(changeMemberName: ChangeMemberName) {
        name = changeMemberName.name
    }
}