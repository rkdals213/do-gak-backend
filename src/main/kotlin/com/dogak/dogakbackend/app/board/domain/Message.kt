package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.common.infra.TableTimeStamp
import javax.persistence.*

@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    var board: Board,

    @Column(nullable = false, updatable = false)
    val writerId: Long,

    var content: String
) {
    val tableTimeStamp: TableTimeStamp = TableTimeStamp()
}