package com.dogak.dogakbackend.app.board.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun BoardRepository.findByIdWithCheck(id: Long) = findByIdOrNull(id) ?: throw NoSuchElementException()

interface BoardRepository : JpaRepository<Board, Long> {
}