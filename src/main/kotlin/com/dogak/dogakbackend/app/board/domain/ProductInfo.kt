package com.dogak.dogakbackend.app.board.domain

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
)