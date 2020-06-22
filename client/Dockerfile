FROM openjdk:8
EXPOSE 8081
ADD /target/resttemplate-0.0.1-SNAPSHOT.jar resttemplate-0.0.1-SNAPSHOT.jar
ENV TZ Europe/Moscow
ENTRYPOINT ["java", "-jar", "resttemplate-0.0.1-SNAPSHOT.jar"]