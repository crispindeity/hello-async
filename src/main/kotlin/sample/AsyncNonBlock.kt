package sample

import mu.KotlinLogging
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

val worker: Scheduler = Schedulers.newSingle("worker")

fun main() {
    measureTimeMillis {
        Mono.zip(
            subA(),
            subB(),
        ).subscribeOn(worker).block()
    }.let { logger.debug { ">> elapse : $it ms" } }
}

private fun subA(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "start A" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(worker)
        .doOnNext { logger.debug { "end A" } }
}

private fun subB(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "start B" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(worker)
        .doOnNext { logger.debug { "end B" } }
}
