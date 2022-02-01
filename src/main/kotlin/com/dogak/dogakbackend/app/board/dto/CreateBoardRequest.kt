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
    val productInfoRequest: ProductInfoRequest
) {
    fun toEntity(member: Member): Board {
        return Board(0, title, content, member.id, productInfoRequest.toEntity())
    }
}

data class ProductInfoRequest(
    @field:NotBlank
    val name: String,
    @field:NotNull
    val price: BigDecimal,
    val purchaseTime: PurchaseTimeRequest
) {
    fun toEntity(): ProductInfo {
        return ProductInfo(name, price, purchaseTime.toEntity())
    }
}

data class PurchaseTimeRequest(
    @NotNull
    val year: Int,
    val month: Month
) {
    fun toEntity(): PurchaseTime {
        return PurchaseTime(year, month)
    }
}
