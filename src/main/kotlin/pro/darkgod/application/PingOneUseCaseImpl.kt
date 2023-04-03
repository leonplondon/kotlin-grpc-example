package pro.darkgod.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.darkgod.application.inbound.PingOneUseCase
import pro.darkgod.application.outbound.PingOneService
import pro.darkgod.config.Constants.SERVICE_TWO_ID
import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong

@Service
class PingOneUseCaseImpl @Autowired constructor(
  private val pingOneService: PingOneService,
) : PingOneUseCase {
  override fun ping(): Pong {
    return pingOneService.ping(Ping(SERVICE_TWO_ID))
  }
}
