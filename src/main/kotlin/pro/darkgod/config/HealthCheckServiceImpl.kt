package pro.darkgod.config

import io.grpc.BindableService
import io.grpc.health.v1.HealthCheckResponse.ServingStatus
import io.grpc.protobuf.services.HealthStatusManager

class HealthCheckServiceImpl : HealthChecker {
  private val healthStatusManager = HealthStatusManager()

  override fun getHealthService(): BindableService = this.healthStatusManager.healthService

  override fun setStatus(service: String, status: ServingStatus) {
    println("Service $service is changing its state to ${status.name}")
    healthStatusManager.setStatus(service, status)
  }

  override fun shutdown() {
    healthStatusManager.enterTerminalState()
  }
}
