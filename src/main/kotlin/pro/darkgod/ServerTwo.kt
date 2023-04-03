package pro.darkgod

import io.grpc.BindableService
import io.grpc.ServerBuilder
import io.grpc.health.v1.HealthCheckResponse
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pro.darkgod.adapters.grpc.server.AdapterModule
import pro.darkgod.config.Constants
import pro.darkgod.config.HealthCheckServiceImpl
import pro.darkgod.config.HealthChecker
import pro.darkgod.config.attachShutdownHook
import pro.darkgod.config.bindAppServices
import kotlin.concurrent.thread

@SpringBootApplication
class AppTwo

class ServerTwo(
  private val grpcAdapters: List<BindableService>,
  private val healthChecker: HealthChecker,
  private val port: Int = Constants.SERVICE_TWO_PORT,
) {
  private val logger: Logger = LoggerFactory.getLogger(ServerTwo::class.java)

  fun init() {
    val server = ServerBuilder
      .forPort(port)
      .bindAppServices(grpcAdapters)
      .build()

    server.apply {
      attachShutdownHook(healthChecker)
      start()
      healthChecker.setStatus(
        Constants.OVERALL_SERVICES_ID,
        HealthCheckResponse.ServingStatus.SERVING
      )
      logger.info("gRPC server started on port $port")
    }.awaitTermination()
  }
}

fun main(args: Array<String>) {
  val logger: Logger = LoggerFactory.getLogger(AppTwo::class.java)

  runBlocking {
    logger.info("Let's launch the application")

    logger.info("Create health checker")
    val healthChecker: HealthChecker = HealthCheckServiceImpl()

    thread {
      logger.info("Create and init application")

      ServerTwo(
        grpcAdapters = AdapterModule.getAdaptersTwo(healthChecker),
        healthChecker = healthChecker,
      ).init()
    }

    runApplication<AppTwo>(*args)
  }
}
