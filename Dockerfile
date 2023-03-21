FROM openjdk:19-jdk-slim
COPY . /app
WORKDIR /app
RUN mvn Project-HydroGenera.jar
EXPOSE 7
CMD ["java", "-jar", "target/myapp.jar", "--server.port=7"]