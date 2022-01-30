package com.dogak.dogakbackend.app.board.application

import com.dogak.dogakbackend.app.board.domain.BoardRepository
import com.dogak.dogakbackend.app.board.dto.CreateBoardRequest
import com.dogak.dogakbackend.app.member.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun createBoard(member: Member, createBoardRequest: CreateBoardRequest): Long {
        val board = createBoardRequest.toEntity(member)

        return boardRepository.save(board).id
    }
}