FROM eclipse-temurin:17
EXPOSE 8080
RUN mkdir /fsuvius
COPY target/fsuvius-*.jar /fsuvius/fsuvius.jar
WORKDIR /fsuvius
ENTRYPOINT [ "java", "-jar", "/fsuvius/fsuvius.jar" ]
