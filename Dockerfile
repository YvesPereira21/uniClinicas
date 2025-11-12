FROM eclipse-temurin:21-jre-ubi9-minimal

WORKDIR /uniClinicas

COPY target/uniClinicas-0.0.1-SNAPSHOT.jar uniClinicas.jar

EXPOSE 8080

CMD ["java", "-jar", "uniClinicas.jar"]
