package pro.darkgod.config

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import pro.darkgod.config.Constants.MAX_CALL_RETRIES
import pro.darkgod.config.Constants.SERVICE_ONE_ID
import pro.darkgod.config.Constants.SERVICE_ONE_PORT
import pro.darkgod.config.Constants.SERVICE_TWO_ID
import pro.darkgod.config.Constants.SERVICE_TWO_PORT
import pro.darkgod.proto.ServiceOneGrpc
import pro.darkgod.proto.ServiceTwoGrpc

@Lazy
@Configuration
class Channels {
  private val logger = LoggerFactory.getLogger(Channels::class.java)

  @Bean
  fun serviceOne(): ServiceOneGrpc.ServiceOneBlockingStub {
    logger.info("Creating bean serviceOne")
    val channel = createChannel(SERVICE_ONE_ID, SERVICE_ONE_PORT)
    return ServiceOneGrpc.newBlockingStub(channel)
  }

  @Bean
  fun serviceTwo(): ServiceTwoGrpc.ServiceTwoBlockingStub {
    logger.info("Creating bean serviceTwo")
    val channel = createChannel(SERVICE_TWO_ID, SERVICE_TWO_PORT)
    return ServiceTwoGrpc.newBlockingStub(channel)
  }

  private fun createChannel(service: String, port: Int): ManagedChannel? = ManagedChannelBuilder
    .forTarget("$service:$port")
    .usePlaintext()
    .enableRetry()
    .maxRetryAttempts(MAX_CALL_RETRIES)
    .build()
}
