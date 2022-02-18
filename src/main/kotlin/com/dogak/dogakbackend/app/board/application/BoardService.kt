package com.dogak.dogakbackend.app.board.application

import com.dogak.dogakbackend.app.board.constants.Category
import com.dogak.dogakbackend.app.board.domain.BoardRepository
import com.dogak.dogakbackend.app.board.domain.findByIdWithCheck
import com.dogak.dogakbackend.app.board.dto.*
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.domain.findByIdWithCheck
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository
) {
    fun findBoards(selectBoardRequest: SelectBoardRequest, pageable: Pageable): Page<BoardsResponse> {
        if (selectBoardRequest.category == Category.ALL) {
            return boardRepository.findAll(pageable)
                .map { BoardsResponse(it) }
        }

        return boardRepository.findAllByCategory(selectBoardRequest.category, pageable)
            .map { BoardsResponse(it) }
    }

    fun findBoardDetail(id: Long): BoardDetailResponse {
        val board = boardRepository.findByIdWithCheck(id)
        val member = memberRepository.findByIdWithCheck(board.writerId)

        return BoardDetailResponse(board, member)
    }

    @Transactional
    fun createBoard(member: Member, createBoardRequest: CreateBoardRequest): Long {
        val board = createBoardRequest.toEntity(member)

        return boardRepository.save(board).id
    }

    @Transactional
    fun updateBoard(member: Member, boardId: Long, updateBoardRequest: UpdateBoardRequest): Long {
        val board = boardRepository.findByIdWithCheck(boardId)
        board.update(member, updateBoardRequest)

        return board.id
    }
}