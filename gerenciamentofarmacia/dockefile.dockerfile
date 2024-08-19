# Use a imagem oficial do OpenJDK como base
FROM openjdk:17-jdk-alpine

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o jar do seu projeto para o diretório de trabalho
COPY target/gerenciamentofarmacia-0.0.1-SNAPSHOT.jar app.jar

# Exponha a porta que o Spring Boot vai rodar
EXPOSE 8080

# Defina o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
