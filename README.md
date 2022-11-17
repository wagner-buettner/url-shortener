# README #

### DKB Code Factory URL-Shortener API ###

This is an MVP API for Code Factory to create hashes for received URLs

* Version 0.0.1

### Setup ###
* This project uses [Java 17](https://www.oracle.com/java/technologies/downloads/#java17), [Spring boot](https://spring.io/projects/spring-boot), [PostgreSQL](https://www.postgresql.org/), [Kotlin](https://kotlinlang.org/) and [Docker](https://www.docker.com)
* You can run simply the application with using Docker Compose

### Build and Run ###
Go to the project folder and run:
* ./gradlew clean build -x test
* docker build . -t url-shortener:latest
* docker-compose up -d
- (in case to run locally inside the intelliJ you should run docker-compose to create the Postgres and Redis instances. then, stop the api on docker and run the local profile inside the intelliJ)

### Tests Run ###
#### (after the first build on the step before has been run) ####
* docker-compose up -d
* ./gradlew clean test --info
- and check the report under the project folder: /url-shortener/build/reports/tests/test/index.html

#### One line build and deploy:
* docker-compose down && ./gradlew clean build -x test && docker build . -t url-shortener:latest && docker-compose up -d

#### Postrgres+Docker string connection:
* psql -U url-shortener-usr -d url-shortener-db

### Possible Future Improvements and Features ###
* implement FlywayDB to have the database migrations history and control
* analyze if is necessary to implement OpenAPI(Swagger)
* more test coverage and reports about it
* configure Sonarqube
* analyze how to deal with URL/Hash conflicts
* Kubernetes?
