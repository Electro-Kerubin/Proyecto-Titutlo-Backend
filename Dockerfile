# Usar una imagen base de Java
FROM openjdk:8

# Exponer el puerto 8080
EXPOSE 8443

# Copiar el keystore al contenedor si es necesario
COPY src/main/resources/libros-vinia.p12 /app/libros-vinia.p12

# Agregar el archivo JAR compilado al contenedor
ADD target/Proyecto-Titutlo-Backend-main.jar Proyecto-Titutlo-Backend-main.jar

# Ejecutar la aplicacion Spring Boot
ENTRYPOINT ["java", "-jar", "Proyecto-Titutlo-Backend-main.jar"]