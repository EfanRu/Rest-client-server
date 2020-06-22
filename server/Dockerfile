FROM openjdk:8
EXPOSE 8080
ADD /target/spring_cucumber-0.0.1-SNAPSHOT.jar spring_cucumber-0.0.1-SNAPSHOT.jar
ENV TZ Europe/Moscow
ENTRYPOINT ["java", "-jar", "spring_cucumber-0.0.1-SNAPSHOT.jar", "--spring.datasource.url=jdbc:oracle:thin:@0.0.0.0:1521:ORCL", "--spring.datasource.username=с##root"]