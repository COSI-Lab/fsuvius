# Build
FROM eclipse-temurin:17 as builder
RUN apt update && apt upgrade -y
RUN apt install -y maven

WORKDIR /fsuvius
COPY ./src ./src
COPY ./pom.xml pom.xml
RUN mvn clean package



FROM eclipse-temurin:17
EXPOSE 8080
RUN groupadd --gid 1000 fsuvius
RUN useradd --gid 1000 --uid 1000 --create-home --shell /bin/bash fsuvius
WORKDIR /home/fsuvius/fsuvius
COPY --from=builder /fsuvius/target/fsuvius-*.jar ./fsuvius.jar
RUN chown fsuvius fsuvius.jar
RUN chgrp fsuvius fsuvius.jar
RUN chmod 744 fsuvius.jar
USER fsuvius
ENTRYPOINT [ "java", "-jar", "./fsuvius.jar" ]
