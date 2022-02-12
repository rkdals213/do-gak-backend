package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.dto.PurchaseTimeRequest
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
    fun update(updateBoardRequest: PurchaseTimeRequest) {
        year = updateBoardRequest.year
        month = updateBoardRequest.month
    }
}