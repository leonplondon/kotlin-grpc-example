package pro.darkgod.config

import io.grpc.BindableService
import io.grpc.health.v1.HealthCheckResponse

interface HealthChecker {
  fun getHealthService(): BindableService
  fun setStatus(service: String, status: HealthCheckResponse.ServingStatus)
  fun shutdown()
}
