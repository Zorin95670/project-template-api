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

## Test

### Unit test
Some unit tests need a real database to work, this database can be a docker container or another platform.

You need to initialize database before.

To run unit tests you must execute this commmands on your testing database:

`mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/db -Dflyway.user=#### -Dflyway.password=####`

### Functional test

Behavior-driven development (or BDD) is an agile software development technique that encourages collaboration between developers, QA and non-technical or business participants in a software project.

_behave_ uses tests written in a natural language style, backed up by Python code.

 - behave documentation: http://behave.readthedocs.io/
 - behave.example: https://github.com/behave/behave.example

Required minimum `behave` version: `1.2.6`, so we can use [tagged examples](https://github.com/behave/behave/blob/master/docs/new_and_noteworthy_v1.2.6.rst).

Required minimum python version is 3.7.

In order to run, got to `src/test/behave` folder and please ensure you properly define the following variables, either via environment or via `behave --define`:
 - `URL`: defines the URL of the API. Defaulted to http://localhost:8080/PROJECT_NAME.

You can see documentation about steps using the following command:

`behave --steps-catalog`

## Useful tools

* script `rebuild_dev_docker.sh`: destroy docker container, build application and start docker-compose.

