# Usando a imagem oficial do OpenJDK 21 como base
FROM openjdk:21-jdk-slim

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o JAR da aplicação para o container
COPY target/*.jar xbot-0.0.1-SNAPSHOT.jar

# Expondo a porta em que a aplicação vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/xbot-0.0.1-SNAPSHOT.jar"]

