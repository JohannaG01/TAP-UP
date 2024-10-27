FROM maven:3.9.9-amazoncorretto-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine

WORKDIR /app

ENV TZ=America/Argentina/Buenos_Aires

RUN apk add --no-cache tzdata && \
    ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

RUN apk add --no-cache shadow
RUN useradd -ms /bin/bash appuser

COPY --from=build /app/target/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
