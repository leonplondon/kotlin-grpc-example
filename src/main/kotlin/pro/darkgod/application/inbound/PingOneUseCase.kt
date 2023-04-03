package pro.darkgod.application.inbound

import pro.darkgod.domain.Pong

interface PingOneUseCase {
  fun ping(): Pong
}
