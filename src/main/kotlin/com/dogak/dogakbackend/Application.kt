package com.dogak.dogakbackend

import com.dogak.dogakbackend.app.board.domain.Board
import com.dogak.dogakbackend.app.board.domain.ProductInfo
import com.dogak.dogakbackend.app.board.domain.PurchaseTime
import com.dogak.dogakbackend.app.member.domain.Member
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Month
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@SpringBootApplication
@ConfigurationPropertiesScan
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

                for (i in 0..100) {
                    val board = Board("테스트 상품$i", "내용입니다$i", 1, ProductInfo("상품$i", BigDecimal.valueOf(1000), PurchaseTime(2022, Month.JANUARY)))
                    em.persist(board)
                }
            }
        }
    }
}