package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.dto.UpdateBoardRequest
import java.time.Month
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class PurchaseTime(
    var year: Int,

    @Enumerated(EnumType.STRING)
    var month: Month
) {
    fun update(updateBoardRequest: UpdateBoardRequest) {
        year = updateBoardRequest.purchaseYear
        month = updateBoardRequest.purchaseMonth
    }
}