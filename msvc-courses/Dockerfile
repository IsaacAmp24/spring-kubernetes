# usamos el builder para usar el jdk "alpine" es una distribuicion ligera de linux
FROM alpine/java:21 AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app/msvc-courses

COPY ./pom.xml /app
COPY ./msvc-courses/.mvn ./.mvn
COPY ./msvc-courses/mvnw .
COPY ./msvc-courses/pom.xml .

# empaqueta el proyecto pero sin el codigo fuente [solo las dependencias]
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# copia el codigo fuente en la carpeta [src en la imagen]
COPY ./msvc-courses/src ./src

RUN ./mvnw clean package -DskipTests

# creamos una nueva imagen con el jre y copiamos el jar generado en la imagen anterior
FROM alpine/java:21

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-courses/target/msvc-courses-0.0.1-SNAPSHOT.jar .

# Exponemos el puerto 8002 (puerto definido en application.properties)
EXPOSE 8002

CMD ["java", "-jar", "msvc-courses-0.0.1-SNAPSHOT.jar"]