# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem mínima para rodar a aplicação
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar o JAR gerado do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Variável de ambiente opcional
ENV JAVA_OPTS=""

# Comando para rodar a aplicação
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]