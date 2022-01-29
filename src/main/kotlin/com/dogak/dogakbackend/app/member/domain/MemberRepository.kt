package com.dogak.dogakbackend.app.member.domain

import org.springframework.data.jpa.repository.JpaRepository

fun MemberRepository.findByEmailWithCheck(email: String) = findByEmail(email) ?: throw NoSuchElementException("멤버가 존재하지 않습니다")

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String) : Member?
}