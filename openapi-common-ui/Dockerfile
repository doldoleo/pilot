FROM openjdk:17-jdk-alpine
WORKDIR /usr/app
COPY ./openapi-common-ui/build/libs/*.jar app.jar
CMD ["java", "-Dlogstash.url=logstash.elk.svc.cluster.local:5000", "-jar", "app.jar"]