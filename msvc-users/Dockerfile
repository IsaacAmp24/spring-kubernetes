# usamos el builder para usar el jdk "alpine" es una distribuicion ligera de linux
FROM alpine/java:21 AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app/msvc-users

COPY ./pom.xml /app
COPY ./msvc-users/.mvn ./.mvn
COPY ./msvc-users/mvnw .
COPY ./msvc-users/pom.xml .

# empaqueta el proyecto pero sin el codigo fuente [solo las dependencias]
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# copia el codigo fuente en la carpeta [src en la imagen]
COPY ./msvc-users/src ./src

RUN ./mvnw clean package -DskipTests

FROM alpine/java:21

WORKDIR /app

COPY --from=builder /app/msvc-users/target/msvc-users-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "msvc-users-0.0.1-SNAPSHOT.jar"]