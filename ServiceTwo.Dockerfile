FROM gradle:7.4.2-jdk11 as builder

WORKDIR /dist

COPY --chown=gradle:gradle . /dist

RUN gradle --no-daemon :distZip :installDist

FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

RUN adduser -D nonroot
USER nonroot

COPY --from=builder /dist/build /app/build

ENV CONTAINER=true
ENV GRPC_PORT=9090
ENV SPRING_PORT=9091

EXPOSE $GRPC_PORT

ENTRYPOINT ["/app/build/install/kotlin-grpc-example/bin/svctwo"]