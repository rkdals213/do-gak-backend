package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.dto.UpdateCommentRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.common.infra.TableTimeStamp
import javax.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(length = 200)
    var content: String,

    @ManyToOne
    val board: Board,

    @Column(nullable = false, updatable = false)
    val writerId: Long
) {
    val tableTimeStamp: TableTimeStamp = TableTimeStamp()

    constructor(content: String, board: Board, writerId: Long) : this(
        0,
        content,
        board,
        writerId
    )

    fun update(member: Member, updateCommentRequest: UpdateCommentRequest) {
        check(writerIsEqual(member)) { "본인의 게시글만 수정할 수 있습니다" }
        content = updateCommentRequest.comment
    }

    fun writerIsEqual(member: Member) = member.id == writerId
}