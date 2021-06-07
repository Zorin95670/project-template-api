# Project template api

Template project to create a rest api. Make with Spring and Jersey.

## Usage

Use eclipse to run server.

Use maven to build and run test.

## Docker

1. Build application

To build application on docker, run this command:

`docker build -t PROJECT_NAME .`

2. Run docker compose

`docker-compose up`

In case of error don't forget to delete your container.

3. Development mode

Eclipse doesn't use profiles on building application, so it's always in development mode.

If you use Tomcat in eclipse to run your application, run this command to have a docker database:

`docker run -p 5432:5432 --rm -e POSTGRES_HOST_AUTH_METHOD=trust -v $(pwd)/src/main/resources/db/init.sql:/docker-entrypoint-initdb.d/init.sql -ti postgres:9.6`

## Without docker for production

If you want to use application without docker in production **from scratch**, you need to have postgresql 9.6 and execute the *init database file* before running the application.

Init database file is `src/main/resources/db/init.sql`.

## Run unit test

Some unit tests need a real database to work, this database can be a docker container or another platform.

You need to initialize database before.

To run unit tests you must execute this commmands on your testing database:

`mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/db -Dflyway.user=#### -Dflyway.password=####`

## Useful tools

* script `rebuild_dev_docker.sh`: destroy docker container, build application and start docker-compose.

