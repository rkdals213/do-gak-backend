package com.dogak.dogakbackend.app.board.api

import com.dogak.dogakbackend.app.board.application.BoardService
import com.dogak.dogakbackend.app.board.dto.CreateBoardRequest
import com.dogak.dogakbackend.app.board.dto.SelectBoardRequest
import com.dogak.dogakbackend.app.board.dto.UpdateBoardRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.common.security.Authenticated
import com.dogak.dogakbackend.common.security.MemberClaim
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping
    fun findBoards(selectBoardRequest: SelectBoardRequest, pageable: Pageable): ResponseEntity<Any> {
        val boards = boardService.findBoards(selectBoardRequest, pageable)
        return ResponseEntity.ok(boards)
    }

    @GetMapping("/{boardId}")
    fun findBoardDetail(@PathVariable boardId: Long): ResponseEntity<Any> {
        val board = boardService.findBoardDetail(boardId)
        return ResponseEntity.ok(board)
    }

    @Authenticated
    @PostMapping
    fun createBoard(@MemberClaim member: Member, @RequestBody createBoardRequest: CreateBoardRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(boardService.createBoard(member, createBoardRequest))
    }

    @Authenticated
    @PutMapping("/boardId/{boardId}")
    fun updateBoard(@MemberClaim member: Member, @PathVariable boardId: Long, @RequestBody updateBoardRequest: UpdateBoardRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(boardService.updateBoard(member, boardId, updateBoardRequest))
    }
}