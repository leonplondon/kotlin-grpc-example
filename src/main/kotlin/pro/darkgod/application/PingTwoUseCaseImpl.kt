package pro.darkgod.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.darkgod.application.inbound.PingTwoUseCase
import pro.darkgod.application.outbound.PingTwoService
import pro.darkgod.config.Constants.SERVICE_ONE_ID
import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong

@Service
class PingTwoUseCaseImpl @Autowired constructor(
  private val pingTwoService: PingTwoService
) : PingTwoUseCase {
  override fun ping(): Pong {
    return pingTwoService.ping(Ping(SERVICE_ONE_ID))
  }
}
