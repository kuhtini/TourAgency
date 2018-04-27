#FROM openjdk:8
#ADD target/tour_agency-0.5.jar tour_agency-0.5.jar
#EXPOSE 8086
#ENTRYPOINT ["java", "-jar", "tour_agency-0.5.jar"]

FROM openjdk:8
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} tour_agency-0.5.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tour_agency-0.5.jar"]