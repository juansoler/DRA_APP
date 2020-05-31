FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY rest/pom.xml /tmp/
COPY rest/src /tmp/src/
WORKDIR /tmp/
RUN mvn package


FROM openjdk:11

RUN mkdir /home/apiREST/
WORKDIR /home/apiREST/
#COPY ./rest/target/rest-0.0.1-SNAPSHOT.jar /home/apiREST/apiREST.jar
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/rest-0.0.1-SNAPSHOT.jar /home/apiREST/apiREST.jar

EXPOSE 8080

CMD [ "java", "-jar", "apiREST.jar" ]