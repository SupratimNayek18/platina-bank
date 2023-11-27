#Base image containing java runtime
FROM openjdk:17-jdk-slim

#Info on who maintains the image
MAINTAINER platinabank

#copying the jar from target to root directory
COPY target/cards-0.0.1-SNAPSHOT.jar cards-0.0.1-SNAPSHOT.jar

#execution command info of the application
ENTRYPOINT ["java","-jar","cards-0.0.1-SNAPSHOT.jar"]