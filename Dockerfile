FROM openjdk

WORKDIR  /app

COPY build/libs/spring_boot-0.0.1-SNAPSHOT.jar /app/docker-spring.jar

ENTRYPOINT ["java", "-jar", "docker-spring.jar"]