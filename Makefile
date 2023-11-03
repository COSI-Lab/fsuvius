all: build_jar build_container run

build_jar:
	mvn -f pom.xml clean install package

build_container:
	docker compose build

run:
	docker compose up --detach

clean:
	docker image prune

attach:
	docker container exec -it fsuvius /bin/bash

initial-setup:
	mkdir data