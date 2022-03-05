package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.constants.Status
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class BuyerInformation(
    @Enumerated(EnumType.STRING)
    var status: Status = Status.STAND_BY,

    var buyerId: Long? = null
) {
    fun reserve(buyerId: Long) {
        status = Status.RESERVED
        this.buyerId = buyerId
    }

    fun done() {
        status = Status.DONE
    }

    fun cancelReservation() {
        status = Status.STAND_BY
        buyerId = null
    }
}