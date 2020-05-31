# Dockerfile. 

First of all, copy pom.xml and src files to /tmp to create JARs with the command mvn package.
```shell
FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY rest/pom.xml /tmp/
COPY rest/src /tmp/src/
WORKDIR /tmp/
RUN mvn package
```

Once the jar file is created it's copied from the build 'MAVEN_TOOL_CHAIN' to the workdir where OpenJDK will launch the JAR file. 

```shell
FROM openjdk:11
RUN mkdir /home/apiREST/
WORKDIR /home/apiREST/
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/rest-0.0.1-SNAPSHOT.jar /home/apiREST/apiREST.jar
```

Finally exposes the port 8080 and launch the command to launch the JAR file.

```shell
EXPOSE 8080
CMD [ "java", "-jar", "apiREST.jar" ]
```
