FROM openjdk:11-jdk
VOLUME /tmp

ARG CONFIG_FOLDER
ADD target/reading-is-good-0.0.1-SNAPSHOT.jar reading-is-good.jar
ENTRYPOINT ["java","-jar","reading-is-good.jar"]
