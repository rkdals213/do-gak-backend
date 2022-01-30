package com.dogak.dogakbackend.common.infra

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
class TableTimeStamp(
    @CreatedDate
    var createAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.now()
)