package pro.darkgod.adapters.grpc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.darkgod.adapters.grpc.model.from
import pro.darkgod.adapters.grpc.model.toDomain
import pro.darkgod.application.outbound.PingOneService
import pro.darkgod.config.logMessage
import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong
import pro.darkgod.proto.ServiceOneGrpc.ServiceOneBlockingStub

@Component
class PingOneServiceImpl @Autowired constructor(
  private val serviceOneBlockingStub: ServiceOneBlockingStub,
) : PingOneService {
  private val logPingTwoService = logMessage(PingOneServiceImpl::class.java.simpleName)

  override fun ping(ping: Ping): Pong {
    val pong = serviceOneBlockingStub.ping(from(ping))
      .toDomain()
    logPingTwoService("Response $pong")
    return pong
  }
}
