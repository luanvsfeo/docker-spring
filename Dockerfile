FROM openjdk

WORKDIR  /app

COPY build/libs/spring_boot.jar /app/docker-spring.jar

ENTRYPOINT ["java", "-jar", "docker-spring.jar"]