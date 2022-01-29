package com.dogak.dogakbackend.common.infra

import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
class TableTimeStamp(
    @Cre
    val createAt: LocalDateTime
){
}