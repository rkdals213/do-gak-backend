package com.dogak.dogakbackend.app.board.dto

import com.dogak.dogakbackend.app.board.domain.Board
import com.dogak.dogakbackend.app.board.domain.ProductInfo
import com.dogak.dogakbackend.app.board.domain.PurchaseTime
import com.dogak.dogakbackend.app.member.domain.Member
import java.math.BigDecimal
import java.time.Month

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

data class BoardDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val writerId: Long,
    val writerName: String,
    val productInfoResponse: ProductInfoResponse
) {
    constructor(board: Board, writer: Member) : this(
        board.id,
        board.title,
        board.content,
        writer.id,
        writer.name,
        ProductInfoResponse(board.productInfo)
    )
}

data class ProductInfoResponse(
    val name: String,
    val price: BigDecimal,
    val purchaseTime: PurchaseTimeResponse
) {
    constructor(productInfo: ProductInfo) : this(
        productInfo.name,
        productInfo.price,
        PurchaseTimeResponse(productInfo.purchaseTime)
    )
}

data class PurchaseTimeResponse(
    val year: Int,
    val month: Month
) {
    constructor(purchaseTime: PurchaseTime) : this(
        purchaseTime.year,
        purchaseTime.month
    )
}
