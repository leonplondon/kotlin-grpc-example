FROM alpine/curl:3.14 AS tools

ENV GRPC_HEALTH_PROBE_VERSION=v0.4.17
ENV OPEN_TELEMETRY_VERSION=v1.24.0

WORKDIR /tools

RUN curl --request GET \
    --location \
    --silent \
    --output /tools/grpc_health_probe \
    https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64
RUN curl --request GET \
    --location \
    --silent \
    --output /tools/otel-java-agent.jar \
    https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/${OPEN_TELEMETRY_VERSION}/opentelemetry-javaagent.jar
RUN chmod +x /tools/grpc_health_probe

FROM gradle:7.4.2-jdk11 as builder

WORKDIR /dist

COPY --chown=gradle:gradle . /dist

RUN gradle --no-daemon :distZip :installDist

FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

RUN adduser -D nonroot
USER nonroot

COPY --from=builder /dist/build /app/build
COPY --from=tools /tools/otel-java-agent.jar /app
COPY --from=tools /tools/grpc_health_probe /app

ENV JAVA_OPTS="-javaagent:/app/otel-java-agent.jar"
ENV OTEL_SERVICE_NAME="svc-one"
ENV OTEL_METRICS_EXPORTER=logging
ENV OTEL_TRACES_EXPORTER=jaeger
ENV OTEL_EXPORTER_JAEGER_ENDPOINT=http://jaeger:14250
ENV OTEL_EXPORTER_JAEGER_TIMEOUT=10000

ENV CONTAINER=true
ENV GRPC_PORT=8080
ENV SPRING_PORT=8081

EXPOSE $GRPC_PORT

ENTRYPOINT ["/app/build/install/kotlin-grpc-example/bin/svcone"]