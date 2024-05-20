package sample.coffee

import mu.KotlinLogging
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

private val employees = Executors.newFixedThreadPool(3)

fun main() {
    measureTimeMillis {
//        makeCoffee()
        repeat(2) {
            makeCoffee()
        }
        employees.shutdown()
        employees.awaitTermination(100, TimeUnit.SECONDS)
    }.let { logger.debug { ">> elapse : $it ms" } }
}

private fun makeCoffee() {
    val task1 = employees.submit {
        grindCoffee()
        brewCoffee()
    }

    val task2 = employees.submit {
        boilMilk()
        formMilk()
    }

    employees.submit {
        while (!task1.isDone || !task2.isDone) {
            Thread.sleep(10)
        }
        mixCoffeeAndMilk()
    }
}

private fun grindCoffee() {
    logger.debug { "커피 갈기" }
    Thread.sleep(1000)
    logger.debug { "> 커피 가루" }
}

private fun brewCoffee() {
    logger.debug { "커피 추출" }
    Thread.sleep(1000)
    logger.debug { "> 커피 원액" }
}

private fun boilMilk() {
    logger.debug { "우유 데우기" }
    Thread.sleep(1000)
    logger.debug { "> 따뜻한 우유" }
}

private fun formMilk() {
    logger.debug { "우유 거품 내기" }
    Thread.sleep(1000)
    logger.debug { "> 거품 우유" }
}

private fun mixCoffeeAndMilk() {
    logger.debug { "커피와 우유 섞기" }
    Thread.sleep(1000)
    logger.debug { "> 카페 라떼" }
}
