package pro.darkgod.adapters.grpc.server

import io.grpc.BindableService
import pro.darkgod.config.HealthChecker

object AdapterModule {
  fun getAdaptersOne(healthChecker: HealthChecker): List<BindableService> = listOf(
    healthChecker.getHealthService(),
    EmptyServiceImpl(healthChecker),
    ServiceOneAdapter(healthChecker),
  )

  fun getAdaptersTwo(healthChecker: HealthChecker): List<BindableService> = listOf(
    healthChecker.getHealthService(),
    EmptyServiceImpl(healthChecker),
    ServiceTwoAdapter(healthChecker),
  )
}
