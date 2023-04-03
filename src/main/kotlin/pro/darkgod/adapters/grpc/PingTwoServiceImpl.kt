package pro.darkgod.adapters.grpc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.darkgod.adapters.grpc.model.from
import pro.darkgod.adapters.grpc.model.toDomain
import pro.darkgod.application.outbound.PingTwoService
import pro.darkgod.config.logMessage
import pro.darkgod.domain.Ping
import pro.darkgod.domain.Pong
import pro.darkgod.proto.ServiceTwoGrpc.ServiceTwoBlockingStub

@Component
class PingTwoServiceImpl @Autowired constructor(
  private val serviceTwoBlockingStub: ServiceTwoBlockingStub,
) : PingTwoService {
  private val logPingTwoService = logMessage(PingTwoServiceImpl::class.java.simpleName)

  override fun ping(ping: Ping): Pong {
    val pong = serviceTwoBlockingStub.ping(from(ping))
      .toDomain()
    logPingTwoService("Response $pong")
    return pong
  }
}
