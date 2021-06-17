FROM maven:3.6.1-jdk-8-alpine as builder
RUN mkdir -p /root/.m2 && mkdir /root/.m2/repository
COPY settings.xml /root/.m2
COPY . /app/
RUN cd /app && mvn clean package -Dmaven.test.skip=true

FROM tomcat:9.0-jre8-alpine
COPY --from=builder /app/target/PROJECT_NAME.war /usr/local/tomcat/webapps
ENV CATALINA_OPTS=""
EXPOSE 8080
CMD ["catalina.sh", "run"]
