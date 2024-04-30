FROM openjdk:17-jdk-alpine
ARG JARFILE=./openapi-common-ui/build/libs/*.jar
WORKDIR /app # 컨테이너 작업 디렉토리 설정
ADD . /app
COPY ./openapi-common-ui/build/libs/*.jar app.jar
CMD ["java", "-Dlogstash.url=logstash.elk.svc.cluster.local:5000", "-jar", "app.jar"]