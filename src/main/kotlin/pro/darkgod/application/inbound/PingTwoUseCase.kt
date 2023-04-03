package pro.darkgod.application.inbound

import pro.darkgod.domain.Pong

interface PingTwoUseCase {
  fun ping(): Pong
}
