package com.dogak.dogakbackend.app.board.api

import com.dogak.dogakbackend.app.board.application.CommentService
import com.dogak.dogakbackend.app.board.dto.RegisterCommentRequest
import com.dogak.dogakbackend.app.board.dto.UpdateCommentRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.common.security.Authenticated
import com.dogak.dogakbackend.common.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    private val commentService: CommentService
) {
    @GetMapping("/board/{boardId}")
    fun findCommentsOfBoard(@MemberClaim member: Member, @PathVariable boardId: Long): ResponseEntity<Any> {
        val comments = commentService.findCommentsOfBoard(member, boardId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/board/{boardId}")
    @Authenticated
    fun registerComment(@MemberClaim member: Member, @PathVariable boardId: Long, @RequestBody registerCommentRequest: RegisterCommentRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(commentService. registerComment(member, boardId, registerCommentRequest))
    }

    @PatchMapping("/{commentId}")
    @Authenticated
    fun updateComment(@MemberClaim member: Member, @PathVariable commentId: Long, @RequestBody updateCommentRequest: UpdateCommentRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(commentService.updateComment(member, commentId, updateCommentRequest))
    }
}