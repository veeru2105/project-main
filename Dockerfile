From openjdk:21-ea-17-jdk
VOLUME /tmp
COPY /target/project-0.0.1-SNAPSHOT.jar  project.jar
ENTRYPOINT ["java","-jar","project.jar"]
