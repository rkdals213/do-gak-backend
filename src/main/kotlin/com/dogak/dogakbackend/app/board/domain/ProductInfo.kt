package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.dto.ProductRequest
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class ProductInfo(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: BigDecimal,

    @Embedded
    var purchaseTime: PurchaseTime
) {
    fun update(updateBoardRequest: ProductRequest) {
        name = updateBoardRequest.name
        price = BigDecimal.valueOf(updateBoardRequest.price)
        purchaseTime.update(updateBoardRequest.purchaseTime)
    }
}