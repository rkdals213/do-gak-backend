package com.dogak.dogakbackend.app.member.domain

import com.dogak.dogakbackend.app.member.dto.ChangeMemberName
import com.dogak.dogakbackend.app.member.dto.KakaoMemberInfo
import com.dogak.dogakbackend.common.infra.TableTimeStamp
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

    fun changeName(changeMemberName: ChangeMemberName) {
        name = changeMemberName.name
    }

    fun validateLoginInfo(kakaoMemberInfo: KakaoMemberInfo) {
        require(email == kakaoMemberInfo.kakao_account.email) { "로그인 정보가 일치하지 않습니다" }
    }

    companion object {
        val DUMMY: Member
            get() = Member(-1, "", "")
    }
}