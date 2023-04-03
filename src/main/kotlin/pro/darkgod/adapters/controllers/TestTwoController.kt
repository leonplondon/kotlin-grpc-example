package pro.darkgod.adapters.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pro.darkgod.application.inbound.PingTwoUseCase
import pro.darkgod.domain.Pong
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class TestTwoController @Autowired constructor(
  private val pingTwoUseCase: PingTwoUseCase,
) {

  @GetMapping("/svc-two")
  fun svcTwo(): Mono<Pong> {
    val pong = pingTwoUseCase.ping()
    return Mono.just(pong)
  }
}
