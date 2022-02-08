package com.dogak.dogakbackend.app.board.dto

import com.dogak.dogakbackend.app.board.domain.Board
import com.dogak.dogakbackend.app.board.domain.ProductInfo
import com.dogak.dogakbackend.app.board.domain.PurchaseTime
import com.dogak.dogakbackend.app.member.domain.Member
import java.math.BigDecimal
import java.time.Month
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateBoardRequest(
    @field:NotBlank
    val title: String,
    val content: String,
    val productName: String,
    val productPrice: Long,
    val purchaseYear: Int,
    val purchaseMonth: Month
) {
    fun toEntity(member: Member): Board {
        return Board(0, title, content, member.id, ProductInfo(productName, BigDecimal.valueOf(productPrice), PurchaseTime(purchaseYear, purchaseMonth)))
    }
}

data class UpdateBoardRequest(
    @field:NotBlank
    val title: String,
    val content: String,
    val productName: String,
    val productPrice: Long,
    val purchaseYear: Int,
    val purchaseMonth: Month
)