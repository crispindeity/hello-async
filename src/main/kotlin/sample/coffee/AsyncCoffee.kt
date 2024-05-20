package sample.coffee

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}
private val employee = Schedulers.newSingle("employee")

fun main() {
    measureTimeMillis {
        Flux.range(1, 1000).flatMap {
            makeCoffee()
        }.subscribeOn(employee).blockLast()
    }.let { logger.debug { ">> elapse : $it ms" } }
}

private fun makeCoffee(): Mono<Unit> {
    return Mono.zip(
        grindCoffee().then(brewCoffee()),
        boilMilk().then(formMilk()),
    ).then(mixCoffeeAndMilk())
}

private fun grindCoffee(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "커피 갈기" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(employee)
        .doOnNext { logger.debug { "> 커피 가루" } }
}

private fun brewCoffee(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "커피 추출" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(employee)
        .doOnNext { logger.debug { "> 커피 원액" } }
}

private fun boilMilk(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "우유 데우기" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(employee)
        .doOnNext { logger.debug { "> 따뜻한 우유" } }
}

private fun formMilk(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "우유 거품 내기" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(employee)
        .doOnNext { logger.debug { "> 거품 우유" } }
}

private fun mixCoffeeAndMilk(): Mono<Unit> {
    return Mono.fromCallable { logger.debug { "커피와 우유 섞기" } }
        .delayElement(Duration.ofSeconds(1))
        .publishOn(employee)
        .doOnNext { logger.debug { "> 카페 라떼" } }
}
