FROM maven:3.8.7 AS deps
ARG WORKDIR=/opt/app
WORKDIR ${WORKDIR}

COPY pom.xml .
RUN mvn dependency:go-offline

# Stage 2: Build ứng dụng
FROM maven:3.8.7 AS builder
ARG WORKDIR=/opt/app
WORKDIR ${WORKDIR}

COPY --from=deps ${WORKDIR} ${WORKDIR}
COPY --from=deps /root/.m2 /root/.m2

COPY src ./src
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine AS extractor
ARG WORKDIR=/opt/app
ARG JAR_FILE=${WORKDIR}/payment-server/target/*.jar
WORKDIR ${WORKDIR}

COPY --from=builder ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jre-alpine
ARG WORKDIR=/opt/app
WORKDIR ${WORKDIR}

ENV TZ=Asia/Ho_Chi_Minh
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && addgroup -S twoup && adduser -S twoup -G twoup

RUN chown -R twoup:twoup ${WORKDIR}
USER twoup

COPY --from=extractor ${WORKDIR}/dependencies/ ./
COPY --from=extractor ${WORKDIR}/spring-boot-loader/ ./
COPY --from=extractor ${WORKDIR}/snapshot-dependencies/ ./
COPY --from=extractor ${WORKDIR}/application/ ./

ENTRYPOINT ["java", "-XX:+UseG1GC", "-Xms512m", "-Xmx768m", "-Xss512k", "-XX:MaxRAM=72m", "org.springframework.boot.loader.JarLauncher"]
