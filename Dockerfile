# ── build stage ──────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS build

# Allow passing Maven args and a cache-bust token from CI
ARG MAVEN_ARGS="-DskipTests"
ARG CACHE_BUST=""

WORKDIR /build

# Cache dependencies in a separate layer for faster rebuilds
COPY pom.xml .
RUN mvn -B dependency:go-offline -q

COPY src ./src
# Use batch mode and allow overriding Maven args; include CACHE_BUST in a harmless echo to bust cache when needed
RUN echo "CACHE_BUST=${CACHE_BUST}" > /tmp/cache_bust.txt && mvn -B ${MAVEN_ARGS} package -q

# ── runtime stage ─────────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Run as a non-root user for security
RUN addgroup -S petstore && adduser -S petstore -G petstore

COPY --from=build /build/target/*.jar app.jar

RUN chown petstore:petstore app.jar

USER petstore

# Expose application port
EXPOSE 8080

# Ensure curl is available for healthchecks (small and widely available)
RUN apk add --no-cache curl

# Health check using the Spring Boot Actuator endpoint
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Enable container-aware JVM memory and CPU management
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
