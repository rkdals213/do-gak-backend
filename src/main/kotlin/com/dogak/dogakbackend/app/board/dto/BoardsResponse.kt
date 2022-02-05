package com.dogak.dogakbackend.app.board.dto

import com.dogak.dogakbackend.app.board.domain.Board

data class BoardsResponse(
    val id: Long,
    val title: String,
    val content: String
) {
    constructor(board: Board) : this(
        board.id,
        board.title,
        board.content
    )
}
