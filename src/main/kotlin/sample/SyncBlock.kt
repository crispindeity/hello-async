package sample

import mu.KotlinLogging
import kotlin.time.measureTime

private val logger = KotlinLogging.logger {}

fun main() {
    measureTime {
        subA()
        subB()
    }.let { logger.debug { ">> elapse : $it ms" } }
}

private fun subA() {
    logger.debug { "start A" }
    Thread.sleep(1000)
    logger.debug { "end A" }
}

private fun subB() {
    logger.debug { "start B" }
    Thread.sleep(1000)
    logger.debug { "end B" }
}
