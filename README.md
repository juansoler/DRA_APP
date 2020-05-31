# Integration of services with Docker.
## Rapid Application Development 2020 Universidad de Almería

This is an university practice just for education purposes. 

## Instructions:

Just docker-compuse up and it works!!!

```shell
docker-compose -f "docker-compose.yml" up -d --build
```


## About the docker-compose file.

This work is about integration of database service, backend and frontend with Docker. 

We will use the following technologies.

* [Docker](https://www.docker.com/) for encapsulate and orquestrate the services.
* [PostgreSQL](https://www.postgresql.org/) for database service.
* [VueJS](https://www.vuejs.org/) for frontend development.
* [Spring Boot](https://spring.io/projects/spring-boot) for backend development.
* [Vuetify]( https://vuetifyjs.com/en/), this is additionally for Material Design effects in the frontend.
* [Nginx](https://www.nginx.com/) for serving the static files generated.
* [Maven](https://maven.apache.org/) for management dependencies, testing and documentation of projects.

## Now, it's time to comment the docker-compose file.

This stands up the service for frontend and exposes it at the port 80. 

Basically what does it's compiles the VueApp and generate the dist folder and them copy all the files to the public folder folder of nginx (html folder).


```shell
frontend:
        build: frontend
        ports:
          - '80:80'
```

This service is for the backend and exposes it at the port 8080. 


```shell
  apirest:
        build: apirest
        restart: always
        ports:
            - '8080:8080'
```

The URL used in the frontend is https://localhost:8080/api.

## Detail of the dockerfile in apirest folder.

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




This service doesn't use an Dockerfile to setup the service. It just pull the image postgres, set the enviorment variables and expose the port. 

```shell
db:
        image: postgres
        restart: always
        environment:
            POSTGRES_USER: user
            POSTGRES_PASSWORD: pass
            POSTGRES_DB: apiREST
        ports:
             - '5432:5432'
```

You must take care about the user, pass and database name which is configured in the file application.properties in the rest application made with Spring Boot.

This is the file apirest\rest\src\main\resources\application.properties
```shell
server.servlet.context-path=/api
spring.datasource.url=jdbc:postgresql://db:5432/apiREST
spring.datasource.username=user
spring.datasource.password=pass
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect
spring.jpa.hibernate.ddl-auto= update
spring.jpa.show-sql=true
spring.jpa.generate-ddl=false
```







