package sample.coffee

import mu.KotlinLogging
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

fun main() {
    measureTimeMillis {
        repeat(2) {
            makeCoffee()
        }
    }.let { logger.debug { ">> elapse : $it ms" } }
}

private fun makeCoffee() {
    grindCoffee()
    brewCoffee()
    boilMilk()
    formMilk()
    mixCoffeeAndMilk()
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
