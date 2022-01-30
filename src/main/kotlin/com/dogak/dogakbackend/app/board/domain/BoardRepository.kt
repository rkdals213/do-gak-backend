package com.dogak.dogakbackend.app.board.domain

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
}