# Usa a imagem do JDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Define diretório de trabalho
WORKDIR /app

# Copia o .jar para dentro do container
COPY target/*.jar app.jar

# Expõe a porta usada pela aplicação
EXPOSE 9093

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
