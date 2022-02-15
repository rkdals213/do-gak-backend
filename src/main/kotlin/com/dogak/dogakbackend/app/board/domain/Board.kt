package com.dogak.dogakbackend.app.board.domain

import com.dogak.dogakbackend.app.board.constants.Category
import com.dogak.dogakbackend.app.board.dto.UpdateBoardRequest
import com.dogak.dogakbackend.app.member.domain.Member
import com.dogak.dogakbackend.common.infra.TableTimeStamp
import javax.persistence.*

@Entity
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    var title: String,

    @Lob
    var content: String,

    @Column(nullable = false, updatable = false)
    val writerId: Long,

    @Enumerated(EnumType.STRING)
    var category: Category,

    @Embedded
    var productInfo: ProductInfo
) {
    val tableTimeStamp: TableTimeStamp = TableTimeStamp()

    constructor(title: String, content: String, writerId: Long, category: Category, productInfo: ProductInfo) : this(0, title, content, writerId, category, productInfo)

    init {
        require(category != Category.ALL) { "카테고리는 전체로 설정할 수 없습니다" }
    }

    fun update(member: Member, updateBoardRequest: UpdateBoardRequest) {
        check(writerIsEqual(member)) { "본인의 게시글만 수정할 수 있습니다" }
        title = updateBoardRequest.title
        content = updateBoardRequest.content
        productInfo.update(updateBoardRequest.productInfo)
    }

    private fun writerIsEqual(member: Member) = member.id == writerId


}