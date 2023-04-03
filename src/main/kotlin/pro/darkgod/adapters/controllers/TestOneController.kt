package pro.darkgod.adapters.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pro.darkgod.application.inbound.PingOneUseCase
import pro.darkgod.domain.Pong
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class TestOneController @Autowired constructor(
  private val pingOneUseCase: PingOneUseCase,
) {
  @GetMapping("/svc-one")
  fun svcOne(): Mono<Pong> {
    val pong = pingOneUseCase.ping()
    return Mono.just(pong)
  }

}
