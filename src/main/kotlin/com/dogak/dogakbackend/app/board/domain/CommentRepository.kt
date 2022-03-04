package com.dogak.dogakbackend.app.board.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun CommentRepository.findByIdWithCheck(id: Long) = findByIdOrNull(id) ?: throw NoSuchElementException()

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByBoard(board: Board): List<Comment>
}