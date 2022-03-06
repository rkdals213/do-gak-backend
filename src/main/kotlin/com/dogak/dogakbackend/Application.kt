package com.dogak.dogakbackend

import com.dogak.dogakbackend.app.board.constants.Category
import com.dogak.dogakbackend.app.board.domain.Board
import com.dogak.dogakbackend.app.board.domain.Comment
import com.dogak.dogakbackend.app.board.domain.ProductInfo
import com.dogak.dogakbackend.app.board.domain.PurchaseTime
import com.dogak.dogakbackend.app.member.domain.Member
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Month
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableFeignClients
class DoGakBackendApplication

fun main(args: Array<String>) {
    runApplication<DoGakBackendApplication>(*args)
}


@Component
class InitService(
    private val init: Init
) {

    @PostConstruct
    fun init() {
        init.init()
    }

    companion object {
        @Component
        @Transactional
        class Init(private val em: EntityManager) {
            fun init() {
                val member = Member("shinjk213@nate.com", "강민형")
                em.persist(member)

                val member2 = Member("shinjk213@nate.com2", "강민형")
                em.persist(member2)

                for (i in 0..29) {
                    val board = Board("테스트 상품$i", "내용입니다$i", 1, Category.CUSTOM, ProductInfo("상품$i", BigDecimal.valueOf(1000), PurchaseTime(2022, Month.JANUARY)))
                    val comment1 = Comment("댓글 1", board, member.id)
                    val comment2 = Comment("댓글 2", board, member.id)
                    val comment3 = Comment("댓글 3", board, member2.id)
                    em.persist(board)
                    em.persist(comment1)
                    em.persist(comment2)
                    em.persist(comment3)
                }

                for (i in 30..59) {
                    val board = Board("테스트 상품$i", "내용입니다$i", 2, Category.READY_MADE, ProductInfo("상품$i", BigDecimal.valueOf(1000), PurchaseTime(2022, Month.JANUARY)))
                    em.persist(board)
                }

                for (i in 60..99) {
                    val board = Board("테스트 상품$i", "내용입니다$i", 2, Category.TRANSFER, ProductInfo("상품$i", BigDecimal.valueOf(1000), PurchaseTime(2022, Month.JANUARY)))
                    em.persist(board)
                }
            }
        }
    }
}