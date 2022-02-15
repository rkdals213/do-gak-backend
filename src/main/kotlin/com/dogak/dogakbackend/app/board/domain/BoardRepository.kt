package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.constants.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun BoardRepository.findByIdWithCheck(id: Long) = findByIdOrNull(id) ?: throw NoSuchElementException()

interface BoardRepository : JpaRepository<Board, Long> {
    fun findAllByCategory(category: Category, pageable: Pageable): Page<Board>
}