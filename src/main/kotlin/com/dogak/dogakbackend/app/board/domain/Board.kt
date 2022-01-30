package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.common.infra.TableTimeStamp
import javax.persistence.*

@Entity
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val title: String,

    @Lob
    val content: String,

    @Column(nullable = false, updatable = false)
    val writerId: Long,

    @Embedded
    var productInfo: ProductInfo

) {
    val tableTimeStamp: TableTimeStamp = TableTimeStamp()

}