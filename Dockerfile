# syntax=docker/dockerfile:1

# Builder stage: use Maven + JDK (Alpine)
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
ARG MAVEN_ARGS="-DskipTests"
ARG CACHEBUST=1

WORKDIR /workspace

# Copy only what is needed early to leverage layer cache.
# Note: the base image provides Maven, so the Maven wrapper directory (.mvn) and mvnw
# are optional in this build context; copying a missing .mvn caused the previous error.
COPY pom.xml ./

# optional cachebust file so CI can force rebuilds when snapshots change
RUN echo "cachebust=${CACHEBUST}" > /cachebust || true

# Copy sources and build (MAVEN_ARGS can be overridden at build time)
COPY src ./src
RUN mvn -B package ${MAVEN_ARGS}

# Runtime stage: small JRE image (Alpine)
FROM eclipse-temurin:21-jre-alpine
ARG CACHEBUST=1

# install curl as root for healthchecks, then switch to unprivileged user
USER root
RUN apk add --no-cache --no-progress curl \
  && addgroup -S app && adduser -S -G app app

USER app
WORKDIR /app

# copy built jar (use wildcard to be resilient to artifactId/version)
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080

# healthcheck using curl (curl installed above)
HEALTHCHECK --interval=30s --timeout=5s CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh","-c","exec java -jar /app/app.jar"]
