FROM eclipse-temurin:17-jdk-alpine
EXPOSE 80
RUN mkdir /fsuvius
COPY target/fsuvius-*.jar /fsuvius/fsuvius.jar
RUN ls /fsuvius
WORKDIR /fsuvius
ENTRYPOINT [ "java", "-jar", "/fsuvius/fsuvius.jar" ]