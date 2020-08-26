FROM java:8

ADD target/swagger-example-0.0.1-SNAPSHOT.jar /usr/local/jar/

RUN mv /usr/local/jar/swagger-example-0.0.1-SNAPSHOT.jar /usr/local/jar/app.jar

EXPOSE 8085

CMD ["java","-jar","/usr/local/jar/app.jar"]