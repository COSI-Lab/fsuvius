FROM eclipse-temurin:17-jdk-alpine
EXPOSE 80
RUN mkdir /fsuvius
COPY target/fsuvius-0.4.0.jar /fsuvius/app.jar
RUN ls /fsuvius
WORKDIR /fsuvius
ENTRYPOINT [ "java", "-jar", "/fsuvius/app.jar" ]