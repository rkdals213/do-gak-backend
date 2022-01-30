package com.dogak.dogakbackend.app.board.api

import com.dogak.dogakbackend.app.board.application.BoardService
import com.dogak.dogakbackend.app.board.dto.CreateBoardRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.common.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    fun createBoard(@MemberClaim member: Member, @RequestBody createBoardRequest: CreateBoardRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(boardService.createBoard(member, createBoardRequest))
    }
}