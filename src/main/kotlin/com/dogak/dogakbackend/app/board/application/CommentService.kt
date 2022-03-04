package com.dogak.dogakbackend.app.board.application

import com.dogak.dogakbackend.app.board.domain.BoardRepository
import com.dogak.dogakbackend.app.board.domain.CommentRepository
import com.dogak.dogakbackend.app.board.domain.findByIdWithCheck
import com.dogak.dogakbackend.app.board.dto.CommentResponse
import com.dogak.dogakbackend.app.board.dto.UpdateCommentRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.app.member.domain.MemberRepository
import com.dogak.dogakbackend.app.member.domain.findByIdWithCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val memberRepository: MemberRepository
) {

    fun findCommentOfBoard(member: Member, boardId: Long): List<CommentResponse> {
        val board = boardRepository.findByIdWithCheck(boardId)
        val comments = commentRepository.findByBoard(board)
        val writer = memberRepository.findByIdWithCheck(board.writerId)

        return comments.map { comment ->
            CommentResponse(comment, writer).also { commentResponse ->
                commentResponse.isWriter = writerCheck(member, writer)
            }
        }
    }

    private fun writerCheck(member: Member, writer: Member) = member.id == writer.id

    @Transactional
    fun updateComment(member: Member, commentId: Long, updateCommentRequest: UpdateCommentRequest): Long {
        val comment = commentRepository.findByIdWithCheck(commentId)
        comment.update(member, updateCommentRequest)

        return comment.board.id
    }
}