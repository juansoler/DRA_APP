FROM openjdk:11

RUN mkdir /home/apiREST/
WORKDIR /home/apiREST/
COPY ./rest-0.0.1-SNAPSHOT.jar /home/apiREST/apiREST.jar
EXPOSE 8082

CMD [ "java", "-jar", "apiREST.jar" ]