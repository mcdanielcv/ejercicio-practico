# Usar una imagen base de Java
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app
RUN cd /app

# Copiar el archivo JAR del microservicio al contenedor
COPY target/cliente-persona-0.0.1-SNAPSHOT.jar cliente-persona-microservice.jar

# Exponer el puerto que usa el microservicio
EXPOSE 8080

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "cliente-persona-microservice.jar"]