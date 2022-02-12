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
    val productInfo: ProductRequest
) {
    fun toEntity(member: Member): Board {
        return Board(title, content, member.id, ProductInfo(productInfo.name, BigDecimal.valueOf(productInfo.price), PurchaseTime(productInfo.purchaseTime.year, productInfo.purchaseTime.month)))
    }
}

data class UpdateBoardRequest(
    @field:NotBlank
    val title: String,
    val content: String,
    val productInfo: ProductRequest
)

data class ProductRequest(
    val name: String,
    val price: Long,
    val purchaseTime: PurchaseTimeRequest
)

data class PurchaseTimeRequest(
    val year: Int,
    val month: Month
)