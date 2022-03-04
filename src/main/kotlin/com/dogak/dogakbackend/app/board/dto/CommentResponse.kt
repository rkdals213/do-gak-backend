package com.dogak.dogakbackend.app.board.dto

import com.dogak.dogakbackend.app.board.domain.Comment
import com.dogak.dogakbackend.app.member.domain.Member
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val writerId: Long,
    val writerName: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
) {
    var isWriter: Boolean = false

    constructor(comment: Comment, writer: Member) : this(
        comment.id,
        comment.content,
        comment.writerId,
        writer.name,
        comment.tableTimeStamp.createAt,
        comment.tableTimeStamp.modifiedAt
    )
}