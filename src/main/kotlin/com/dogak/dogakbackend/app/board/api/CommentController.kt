package com.dogak.dogakbackend.app.board.api

import com.dogak.dogakbackend.app.board.application.CommentService
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
        val comments = commentService.findCommentOfBoard(member, boardId)
        return ResponseEntity.ok(comments)
    }

    @PatchMapping("/{commentId}")
    @Authenticated
    fun updateComment(@MemberClaim member: Member, @PathVariable commentId: Long, @RequestBody updateCommentRequest: UpdateCommentRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(commentService.updateComment(member, commentId, updateCommentRequest))
    }
}